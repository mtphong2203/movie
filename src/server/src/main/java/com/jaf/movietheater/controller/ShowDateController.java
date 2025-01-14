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

import com.jaf.movietheater.dtos.showdate.ShowDateCreateUpdateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateMasterDTO;
import com.jaf.movietheater.mappers.CustomPagedResponse;
import com.jaf.movietheater.services.ShowDateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/showDates")
@Tag(name = "ShowDates", description = "APIs for managing showDates")
public class ShowDateController {
    private final ShowDateService showDateService;
    private final PagedResourcesAssembler<ShowDateMasterDTO> pagedResourcesAssembler;

    public ShowDateController(ShowDateService showDateService, PagedResourcesAssembler<ShowDateMasterDTO> pagedResourcesAssembler) {
        this.showDateService = showDateService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // get all
    @GetMapping
    @Operation(summary = "Get all showDates")
    @ApiResponse(responseCode = "200", description = "Return all showDates")
    public ResponseEntity<List<ShowDateMasterDTO>> getAll() {
        var showDateDTOs = showDateService.findAll();
        return ResponseEntity.ok(showDateDTOs);
    }

    // get by name
    @GetMapping("/searchByName")
    @Operation(summary = "Search showDate by showDate name")
    @ApiResponse(responseCode = "200", description = "Return showDates that match the showDate name")
    public ResponseEntity<List<ShowDateMasterDTO>> getByName(@RequestParam(required=false) String keyword) {
        var showDateDTOs = showDateService.findByName(keyword);
        return ResponseEntity.ok(showDateDTOs);
    }

    // get by keyword and pagination
    @GetMapping("/search-paginated")
    @Operation(summary = "Search showDates with pagination")
    @ApiResponse(responseCode = "200", description = "Return showDates that match the keyword with pagination")
    public ResponseEntity<?> getPagination(@RequestParam(required=false) String keyword, @RequestParam(required=false, defaultValue="name") String sortBy, @RequestParam(required=false, defaultValue="asc") String order, @RequestParam(required=false, defaultValue="0") int page, @RequestParam(required=false, defaultValue="10") int size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var showDateDTOs = showDateService.findPagination(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(showDateDTOs);

        Collection<EntityModel<ShowDateMasterDTO>> data = pageModel.getContent();

        var links = pageModel.getLinks();

        var response = new CustomPagedResponse<EntityModel<ShowDateMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
    }

    // get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get showDate by id")
    @ApiResponse(responseCode = "200", description = "Return showDate that match the id")
    public ResponseEntity<ShowDateMasterDTO> getById(@PathVariable UUID id) {
        var showDateMasterDTO = showDateService.findById(id);
        return ResponseEntity.ok(showDateMasterDTO);
    }

    // create
    @PostMapping()
    @Operation(summary = "Create new moive")
    @ApiResponse(responseCode = "200", description = "Return new created showDate")
    @ApiResponse(responseCode = "400", description = "Return error message if create failed")
    public ResponseEntity<?> create(@Valid @RequestBody ShowDateCreateUpdateDTO showDateCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newShowDate = showDateService.create(showDateCreateUpdateDTO);

        if (newShowDate == null) {
            return ResponseEntity.badRequest().body("Fail to create new showDate");
        }

        return ResponseEntity.ok(newShowDate);
    }

    // edit
    @PutMapping("/{id}")
    @Operation(summary = "Update showDate by id")
    @ApiResponse(responseCode = "200", description = "Return updated showDate")
    @ApiResponse(responseCode = "400", description = "Return error message if updated failed")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody ShowDateCreateUpdateDTO showDateCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedShowDate = showDateService.update(id, showDateCreateUpdateDTO);

        if (updatedShowDate == null) {
            return ResponseEntity.badRequest().body("Failed to update showDate");
        }

        return ResponseEntity.ok(updatedShowDate);
    }

    // delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove showDate by id")
    @ApiResponse(responseCode = "200", description = "Return true id delete successfully")
    @ApiResponse(responseCode = "400", description = "Return error message if delete failed")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var result = showDateService.delete(id);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to delete showDate");
        }

        return ResponseEntity.ok(result);
    }
}
