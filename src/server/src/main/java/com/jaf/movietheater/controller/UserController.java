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

import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.dtos.user.UserUpdateDTO;
import com.jaf.movietheater.response.CustomResponseData;
import com.jaf.movietheater.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User APIs", description = "User management")
public class UserController {
    private final UserService userService;
    private final PagedResourcesAssembler<UserMasterDTO> pagedResource;

    public UserController(UserService userService, PagedResourcesAssembler<UserMasterDTO> pagedResource) {
        this.userService = userService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    @Operation(summary = "Get all user")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<UserMasterDTO>> getAll() {
        var userMasters = userService.getAll();
        return ResponseEntity.ok(userMasters);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<UserMasterDTO> getById(@PathVariable UUID id) {
        var userMaster = userService.getById(id);
        return ResponseEntity.ok(userMaster);
    }

    @GetMapping("/searchByName")
    @Operation(summary = "Search user by name")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<UserMasterDTO>> searchByName(@RequestParam(required = false) String keyword) {
        var userMasters = userService.searchByName(keyword);
        return ResponseEntity.ok(userMasters);
    }

    @GetMapping("/search-paginated")
    @Operation(summary = "Search user with paging")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> searchPaginated(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "username") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String orderBy,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) {

        Pageable pageable = null;
        if (orderBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var userMasters = userService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(userMasters);

        Collection<EntityModel<UserMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomResponseData<EntityModel<UserMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateUpdateDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var userMaster = userService.create(userDTO);
        return ResponseEntity.status(201).body(userMaster);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UserCreateUpdateDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var userMaster = userService.update(id, userDTO);
        return ResponseEntity.ok(userMaster);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Admin update user")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var userMaster = userService.update(id, userDTO);
        return ResponseEntity.ok(userMaster);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var isDeleted = userService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}