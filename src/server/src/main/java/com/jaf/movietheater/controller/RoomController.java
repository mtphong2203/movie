package com.jaf.movietheater.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jaf.movietheater.dtos.room.RoomMasterDTO;
import com.jaf.movietheater.dtos.room.RoomSearchDTO;
import com.jaf.movietheater.dtos.room.RoomCreateUpdateDTO;
import com.jaf.movietheater.services.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "Room APIs", description = "Room management")
public class RoomController {
    private final RoomService roomService;
    private final PagedResourcesAssembler<RoomMasterDTO> pagedResourceAssembler;

    public RoomController(RoomService roomService, PagedResourcesAssembler<RoomMasterDTO> pagedResourceAssembler) {
        this.roomService = roomService;
        this.pagedResourceAssembler = pagedResourceAssembler;
    }

    @GetMapping
    @Operation(summary = "Get all rooms with pagination")
    @ApiResponse(responseCode = "200", description = "Return all rooms")
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

        var result = roomService.getAll(keyword, pageable);
        var pagedModel = pagedResourceAssembler.toModel(result);

        return ResponseEntity.ok(pagedModel);
    }
    

    @GetMapping("/{id}")
    @Operation(summary = "Get room by id")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<RoomMasterDTO> getById(@PathVariable UUID id) {
        var room = roomService.getById(id);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/search/cinema/{cinemaId}")
    @Operation(summary = "Get all rooms by cinema id")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> getAllByCinemaId(@PathVariable UUID cinemaId, @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        var rooms = roomService.getAllByCinemaId(cinemaId, pageable);
        var pagedModel = pagedResourceAssembler.toModel(rooms);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    @Operation(summary = "Create new room")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> create(@Valid @RequestBody RoomCreateUpdateDTO roomDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var room = roomService.create(roomDTO);
        return ResponseEntity.status(201).body(room);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody RoomCreateUpdateDTO roomDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var room = roomService.update(id, roomDTO);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var isDeleted = roomService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }
}