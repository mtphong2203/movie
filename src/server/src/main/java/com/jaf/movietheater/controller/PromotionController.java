package com.jaf.movietheater.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jaf.movietheater.dtos.promotion.*;
import com.jaf.movietheater.mappers.CustomPagedResponse;
import com.jaf.movietheater.services.PromotionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/promotions")
@Tag(name = "Promotion APIs", description = "Promotion Management")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PagedResourcesAssembler<PromotionMasterDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Get all promotions")
    @ApiResponse(responseCode = "200", description = "Return all promotions")
    public ResponseEntity<List<PromotionMasterDTO>> getAll() {
        var promotionDTOs = promotionService.getAll();
        return ResponseEntity.ok(promotionDTOs);
    }

    @GetMapping("/search")
    @Operation(summary = "Search promotions with pagination")
    @ApiResponse(responseCode = "200", description = "Return promotions that match the keyword with pagination")
    public ResponseEntity<?> getPagination(@RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var promotionDTOs = promotionService.getAll(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(promotionDTOs);

        Collection<EntityModel<PromotionMasterDTO>> data = pageModel.getContent();

        var links = pageModel.getLinks();

        var response = new CustomPagedResponse<EntityModel<PromotionMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get promotion by id")
    @ApiResponse(responseCode = "200", description = "Return promotion by id")
    public ResponseEntity<PromotionMasterDTO> show(@PathVariable UUID id) {
        var promotionDTO = promotionService.getById(id);

        return ResponseEntity.ok(promotionDTO);
    }

    @PostMapping
    @Operation(summary = "Create new promotion")
    @ApiResponse(responseCode = "201", description = "Return new promotion")
    public ResponseEntity<?> create(@RequestBody @Valid PromotionCreateUpdateDTO promotionCreateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = promotionService.create(promotionCreateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create promotion");
        }

        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update promotion")
    @ApiResponse(responseCode = "200", description = "Return updated promotion")
    public ResponseEntity<?> update(@PathVariable UUID id,
            @RequestBody @Valid PromotionCreateUpdateDTO promotionCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = promotionService.update(id, promotionCreateUpdateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to update promotion");
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete promotion")
    @ApiResponse(responseCode = "200", description = "Return true if delete success")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        var result = promotionService.deleteById(id);

        return ResponseEntity.ok(result);
    }
}