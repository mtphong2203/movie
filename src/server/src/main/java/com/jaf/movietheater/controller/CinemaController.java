package com.jaf.movietheater.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jaf.movietheater.dtos.cinema.CinemaCreateUpdateDTO;
import com.jaf.movietheater.dtos.cinema.CinemaMasterDTO;
import com.jaf.movietheater.dtos.cinema.CinemaSearchDTO;
import com.jaf.movietheater.services.CinemaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cinemas")
public class CinemaController {

    private final CinemaService cinemaService;
    private final PagedResourcesAssembler<CinemaMasterDTO> pagedResourcesAssembler;

    @Autowired
    public CinemaController(CinemaService cinemaService, PagedResourcesAssembler<CinemaMasterDTO> pagedResourcesAssembler) {
        this.cinemaService = cinemaService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    @Operation(summary = "Get all cinemas")
    @ApiResponse(responseCode = "200", description = "Return all cinemas")
    public ResponseEntity<?> index(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var result = cinemaService.getAll(keyword, pageable);
        var pagedModel = pagedResourcesAssembler.toModel(result);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/searchByName")
    @Operation(summary = "Search by name")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<CinemaMasterDTO>> searchByName(@RequestParam(required = false) String keyword) {
        var cinemaMasters = cinemaService.searchByName(keyword);
        return ResponseEntity.ok(cinemaMasters);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cinema by id")
    @ApiResponse(responseCode = "200", description = "Return cinema by id")
    public ResponseEntity<CinemaMasterDTO> show(@PathVariable UUID id) {
        var cinemaDTO = cinemaService.getById(id);

        return ResponseEntity.ok(cinemaDTO);
    }

    @PostMapping()
    @Operation(summary = "Create new cinema")
    @ApiResponse(responseCode = "200", description = "Return new cinema")
    @ApiResponse(responseCode = "400", description = "Return error message")
    public ResponseEntity<?> create(@Valid @RequestBody CinemaCreateUpdateDTO cinemaCreateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = cinemaService.create(cinemaCreateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create cinema");
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit cinema by id")
    @ApiResponse(responseCode = "200", description = "Return edited cinema")
    @ApiResponse(responseCode = "400", description = "Return error message")
    public ResponseEntity<?> update(@PathVariable UUID id,
            @RequestBody @Valid CinemaCreateUpdateDTO cinemaCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = cinemaService.update(id, cinemaCreateUpdateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create cinema");
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete cinema by id")
    @ApiResponse(responseCode = "200", description = "Return true if delete success")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        var result = cinemaService.deleteById(id);

        return ResponseEntity.ok(result);
    }
}