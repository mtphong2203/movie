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

import com.jaf.movietheater.dtos.promotion.PromotionCreateUpdateDTO;
import com.jaf.movietheater.dtos.promotion.PromotionMasterDTO;
import com.jaf.movietheater.dtos.promotion.PromotionSearchDTO;
import com.jaf.movietheater.services.PromotionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    private PagedResourcesAssembler<PromotionMasterDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Get all promotions")
    @ApiResponse(responseCode = "200", description = "Return all promotions")
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

        var result = promotionService.getAll(keyword, pageable);
        var pagedModel = pagedResourcesAssembler.toModel(result);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/search")
    @Operation(summary = "Search promotions by name or location")
    @ApiResponse(responseCode = "200", description = "Return promotions by name or location")
    public ResponseEntity<?> search(@RequestBody PromotionSearchDTO promotionSearchDTO) {
        Pageable pageable = PageRequest.of(promotionSearchDTO.getPage(), promotionSearchDTO.getSize(),
                Sort.by(Sort.Direction.fromString(promotionSearchDTO.getDirection().toString()),
                        promotionSearchDTO.getSort()));

        var result = promotionService.getAll(promotionSearchDTO.getKeyword(), pageable);

        // Convert to PagedModel
        var pagedModel = pagedResourcesAssembler.toModel(result);

        // Extract content without links
        List<PromotionMasterDTO> contentWithoutLinks = pagedModel.getContent().stream()
                .map(entityModel -> entityModel.getContent())
                .collect(Collectors.toList());

        var response = new HashMap<String, Object>();
        response.put("items", contentWithoutLinks);
        response.put("page", pagedModel.getMetadata());
        response.put("links", pagedModel.getLinks());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get promotion by id")
    @ApiResponse(responseCode = "200", description = "Return promotion by id")
    public ResponseEntity<PromotionMasterDTO> show(@PathVariable UUID id) {
        var promotionDTO = promotionService.getById(id);

        return ResponseEntity.ok(promotionDTO);
    }

    @PostMapping()
    @Operation(summary = "Create new promotion")
    @ApiResponse(responseCode = "200", description = "Return new promotion")
    @ApiResponse(responseCode = "400", description = "Return error message")
    public ResponseEntity<?> create(@Valid @RequestBody PromotionCreateUpdateDTO promotionCreateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = promotionService.create(promotionCreateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create promotion");
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit promotion by id")
    @ApiResponse(responseCode = "200", description = "Return edited promotion")
    @ApiResponse(responseCode = "400", description = "Return error message")
    public ResponseEntity<?> update(@PathVariable UUID id,
            @RequestBody @Valid PromotionCreateUpdateDTO promotionCreateUpdateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var result = promotionService.update(id, promotionCreateUpdateDTO);

        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create promotion");
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete promotion by id")
    @ApiResponse(responseCode = "200", description = "Return true if delete success")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        var result = promotionService.deleteById(id);

        return ResponseEntity.ok(result);
    }
}
