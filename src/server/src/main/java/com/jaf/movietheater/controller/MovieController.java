package com.jaf.movietheater.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jaf.movietheater.dtos.movie.MovieCreateUpdateDTO;
import com.jaf.movietheater.dtos.movie.MovieDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.mappers.CustomPagedResponse;
import com.jaf.movietheater.services.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies", description = "APIs for managing movies")
public class MovieController {
    private final MovieService movieService;
    private final PagedResourcesAssembler<MovieDTO> pagedResourcesAssembler;

    public MovieController(MovieService movieService, PagedResourcesAssembler<MovieDTO> pagedResourcesAssembler) {
        this.movieService = movieService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // get all
    @GetMapping
    @Operation(summary = "Get all movies")
    @ApiResponse(responseCode = "200", description = "Return all movies")
    public ResponseEntity<List<MovieDTO>> getAll() {
        var movieDTOs = movieService.findAll();
        return ResponseEntity.ok(movieDTOs);
    }

    // get by name
    @GetMapping("/searchByName")
    @Operation(summary = "Search movie by movie name")
    @ApiResponse(responseCode = "200", description = "Return movies that match the movie name")
    public ResponseEntity<List<MovieDTO>> getByName(@RequestParam(required=false) String keyword) {
        var movieDTOs = movieService.findByName(keyword);
        return ResponseEntity.ok(movieDTOs);
    }

    // get by keyword and pagination
    @GetMapping("/search-paginated")
    @Operation(summary = "Search movies with pagination")
    @ApiResponse(responseCode = "200", description = "Return movies that match the keyword with pagination")
    public ResponseEntity<?> getPagination(@RequestParam(required=false) String keyword, @RequestParam(required=false, defaultValue="name") String sortBy, @RequestParam(required=false, defaultValue="asc") String order, @RequestParam(required=false, defaultValue="0") int page, @RequestParam(required=false, defaultValue="10") int size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var movieDTOs = movieService.findPagination(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(movieDTOs);

        Collection<EntityModel<MovieDTO>> data = pageModel.getContent();

        var links = pageModel.getLinks();

        var response = new CustomPagedResponse<EntityModel<MovieDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
    }

    // get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get movie by id")
    @ApiResponse(responseCode = "200", description = "Return movie that match the id")
    public ResponseEntity<MovieMasterDTO> getById(@PathVariable UUID id) {
        var movieMasterDTO = movieService.findById(id);
        return ResponseEntity.ok(movieMasterDTO);
    }

    // create
    @PostMapping()
    @Operation(summary = "Create new moive")
    @ApiResponse(responseCode = "200", description = "Return new created movie")
    @ApiResponse(responseCode = "400", description = "Return error message if create failed")
    public ResponseEntity<?> create(@Valid @RequestBody MovieCreateUpdateDTO movieCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newMovie = movieService.create(movieCreateUpdateDTO);

        if (newMovie == null) {
            return ResponseEntity.badRequest().body("Fail to create new movie");
        }

        return ResponseEntity.ok(newMovie);
    }

    // edit
    @PutMapping("/{id}")
    @Operation(summary = "Update movie by id")
    @ApiResponse(responseCode = "200", description = "Return updated movie")
    @ApiResponse(responseCode = "400", description = "Return error message if updated failed")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody MovieCreateUpdateDTO movieCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedMovie = movieService.update(id, movieCreateUpdateDTO);

        if (updatedMovie == null) {
            return ResponseEntity.badRequest().body("Failed to update movie");
        }

        return ResponseEntity.ok(updatedMovie);
    }

    // delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove movie by id")
    @ApiResponse(responseCode = "200", description = "Return true id delete successfully")
    @ApiResponse(responseCode = "400", description = "Return error message if delete failed")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var result = movieService.delete(id);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to delete movie");
        }

        return ResponseEntity.ok(result);
    }
}
