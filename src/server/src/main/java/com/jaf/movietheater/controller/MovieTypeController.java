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

import com.jaf.movietheater.dtos.movietype.MovieTypeCreateUpdateDTO;
import com.jaf.movietheater.dtos.movietype.MovieTypeMasterDTO;
import com.jaf.movietheater.mappers.CustomPagedResponse;
import com.jaf.movietheater.services.MovieTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/movieTypes")
@Tag(name = "MovieTypes", description = "APIs for managing movieTypes")
public class MovieTypeController {
    private final MovieTypeService movieTypeService;
    private final PagedResourcesAssembler<MovieTypeMasterDTO> pagedResourcesAssembler;

    public MovieTypeController(MovieTypeService movieTypeService, PagedResourcesAssembler<MovieTypeMasterDTO> pagedResourcesAssembler) {
        this.movieTypeService = movieTypeService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // get all
    @GetMapping
    @Operation(summary = "Get all movieTypes")
    @ApiResponse(responseCode = "200", description = "Return all movieTypes")
    public ResponseEntity<List<MovieTypeMasterDTO>> getAll() {
        var movieTypeDTOs = movieTypeService.findAll();
        return ResponseEntity.ok(movieTypeDTOs);
    }

    // get by name
    @GetMapping("/searchByName")
    @Operation(summary = "Search movieType by movieType name")
    @ApiResponse(responseCode = "200", description = "Return movieTypes that match the movieType name")
    public ResponseEntity<List<MovieTypeMasterDTO>> getByName(@RequestParam(required=false) String keyword) {
        var movieTypeDTOs = movieTypeService.findByName(keyword);
        return ResponseEntity.ok(movieTypeDTOs);
    }

    // get by keyword and pagination
    @GetMapping("/search-paginated")
    @Operation(summary = "Search movieTypes with pagination")
    @ApiResponse(responseCode = "200", description = "Return movieTypes that match the keyword with pagination")
    public ResponseEntity<?> getPagination(@RequestParam(required=false) String keyword, @RequestParam(required=false, defaultValue="name") String sortBy, @RequestParam(required=false, defaultValue="asc") String order, @RequestParam(required=false, defaultValue="0") int page, @RequestParam(required=false, defaultValue="10") int size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var movieTypeDTOs = movieTypeService.findPagination(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(movieTypeDTOs);

        Collection<EntityModel<MovieTypeMasterDTO>> data = pageModel.getContent();

        var links = pageModel.getLinks();

        var response = new CustomPagedResponse<EntityModel<MovieTypeMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
    }

    // get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get movieType by id")
    @ApiResponse(responseCode = "200", description = "Return movieType that match the id")
    public ResponseEntity<MovieTypeMasterDTO> getById(@PathVariable UUID id) {
        var movieTypeMasterDTO = movieTypeService.findById(id);
        return ResponseEntity.ok(movieTypeMasterDTO);
    }

    // create
    @PostMapping()
    @Operation(summary = "Create new moive")
    @ApiResponse(responseCode = "200", description = "Return new created movieType")
    @ApiResponse(responseCode = "400", description = "Return error message if create failed")
    public ResponseEntity<?> create(@Valid @RequestBody MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newMovieType = movieTypeService.create(movieTypeCreateUpdateDTO);

        if (newMovieType == null) {
            return ResponseEntity.badRequest().body("Fail to create new movieType");
        }

        return ResponseEntity.ok(newMovieType);
    }

    // edit
    @PutMapping("/{id}")
    @Operation(summary = "Update movieType by id")
    @ApiResponse(responseCode = "200", description = "Return updated movieType")
    @ApiResponse(responseCode = "400", description = "Return error message if updated failed")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedMovieType = movieTypeService.update(id, movieTypeCreateUpdateDTO);

        if (updatedMovieType == null) {
            return ResponseEntity.badRequest().body("Failed to update movieType");
        }

        return ResponseEntity.ok(updatedMovieType);
    }

    // delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove movieType by id")
    @ApiResponse(responseCode = "200", description = "Return true id delete successfully")
    @ApiResponse(responseCode = "400", description = "Return error message if delete failed")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var result = movieTypeService.delete(id);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to delete movieType");
        }

        return ResponseEntity.ok(result);
    }
}
