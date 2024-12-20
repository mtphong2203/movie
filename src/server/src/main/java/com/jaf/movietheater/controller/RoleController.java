package com.jaf.movietheater.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jaf.movietheater.dtos.roles.RoleCreateUpdateDTO;
import com.jaf.movietheater.dtos.roles.RoleMasterDTO;
import com.jaf.movietheater.response.CustomResponseData;
import com.jaf.movietheater.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    private final PagedResourcesAssembler<RoleMasterDTO> pagedResource;

    public RoleController(RoleService roleService, PagedResourcesAssembler<RoleMasterDTO> pagedResource) {
        this.roleService = roleService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<RoleMasterDTO>> getAll() {
        var roleMasters = roleService.getAll();
        return ResponseEntity.ok(roleMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleMasterDTO> getById(@PathVariable UUID id) {
        var roleMaster = roleService.getById(id);
        return ResponseEntity.ok(roleMaster);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<RoleMasterDTO>> searchByName(@RequestParam(required = false) String keyword) {
        var roleMasters = roleService.searchByName(keyword);
        return ResponseEntity.ok(roleMasters);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPaginated(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String orderBy,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {

        Pageable pageable = null;
        if (orderBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var roleMasters = roleService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(roleMasters);

        Collection<EntityModel<RoleMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomResponseData<EntityModel<RoleMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoleCreateUpdateDTO roleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var roleMaster = roleService.create(roleDTO);
        return ResponseEntity.status(201).body(roleMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody RoleCreateUpdateDTO roleDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var roleMaster = roleService.update(id, roleDTO);
        return ResponseEntity.ok(roleMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var isDeleted = roleService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
