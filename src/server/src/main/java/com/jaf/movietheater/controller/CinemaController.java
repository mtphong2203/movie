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
    @Autowired
    private CinemaService cinemaService;
    private PagedResourcesAssembler<CinemaMasterDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Get all cinemas")
    @ApiResponse(responseCode = "200", description = "Return all cinemas")
    public ResponseEntity<?> index(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy, // Xac dinh truong sap xep
            @RequestParam(required = false, defaultValue = "asc") String order, // Xac dinh chieu sap xep
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

    @GetMapping("/search")
    @Operation(summary = "Search cinemas by name or location")
    @ApiResponse(responseCode = "200", description = "Return cinemas by name or location")
    public ResponseEntity<?> search(@RequestBody CinemaSearchDTO cinemaSearchDTO) {
        Pageable pageable = PageRequest.of(cinemaSearchDTO.getPage(), cinemaSearchDTO.getSize(),
                Sort.by(Sort.Direction.fromString(cinemaSearchDTO.getDirection().toString()),
                        cinemaSearchDTO.getSort()));

        var result = cinemaService.getAll(cinemaSearchDTO.getKeyword(), pageable);

        // Convert to PagedModel
        var pagedModel = pagedResourcesAssembler.toModel(result);

        // Extract content without links
        List<CinemaMasterDTO> contentWithoutLinks = pagedModel.getContent().stream()
                .map(entityModel -> entityModel.getContent())
                .collect(Collectors.toList());

        var response = new HashMap<String, Object>();
        response.put("items", contentWithoutLinks);
        response.put("page", pagedModel.getMetadata());
        response.put("links", pagedModel.getLinks());
        return ResponseEntity.ok(response);
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
