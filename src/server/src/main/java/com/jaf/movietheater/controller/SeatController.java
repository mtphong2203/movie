package com.jaf.movietheater.controller;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seats")
@Tag(name = "Seat APIs", description = "Seat Management")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    @Operation(summary = "Get all seats")
    @ApiResponse(responseCode = "200", description = "Return all seats")
    public List<SeatMasterDTO> getAllSeats() {
        return seatService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get seat by id")
    @ApiResponse(responseCode = "200", description = "Return seat by id")
    public ResponseEntity<?> getSeatById(@PathVariable UUID id) {
        SeatMasterDTO seatDTO = seatService.getById(id);
        if (seatDTO == null) {
            return ResponseEntity.badRequest().body("Seat not found");
        }
        return ResponseEntity.ok(seatDTO);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Get seats by room id")
    @ApiResponse(responseCode = "200", description = "Return seats by room id")
    public List<SeatMasterDTO> getSeatByRoomId(@PathVariable UUID roomId) {
        return seatService.getSeatByRoomId(roomId);
    }

    @PostMapping
    @Operation(summary = "Create new seat")
    @ApiResponse(responseCode = "201", description = "Return new seat")
    public ResponseEntity<?> createSeat(@RequestBody @Valid SeatCreateUpdateDTO seatCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        SeatMasterDTO result = seatService.create(seatCreateUpdateDTO);
        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create seat");
        }

        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update seat")
    @ApiResponse(responseCode = "200", description = "Return updated seat")
    public ResponseEntity<?> updateSeat(@PathVariable UUID id, @RequestBody @Valid SeatCreateUpdateDTO seatCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        SeatMasterDTO result = seatService.update(id, seatCreateUpdateDTO);
        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to update seat");
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete seat")
    @ApiResponse(responseCode = "200", description = "Return true if delete success")
    public ResponseEntity<Boolean> deleteSeat(@PathVariable UUID id) {
        boolean result = seatService.delete(id);
        return ResponseEntity.ok(result);
    }
}