package com.jaf.movietheater.controller;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<SeatDTO> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeatById(@PathVariable UUID id) {
        SeatDTO seatDTO = seatService.getSeatById(id);
        if (seatDTO == null) {
            return ResponseEntity.badRequest().body("Seat not found");
        }
        return ResponseEntity.ok(seatDTO);
    }

    @PostMapping
    public ResponseEntity<?> createSeat(@RequestBody @Valid SeatCreateUpdateDTO seatCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        SeatDTO result = seatService.createSeat(seatCreateUpdateDTO);
        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to create seat");
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeat(@PathVariable UUID id, @RequestBody @Valid SeatCreateUpdateDTO seatCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        SeatDTO result = seatService.updateSeat(id, seatCreateUpdateDTO);
        if (result == null) {
            return ResponseEntity.badRequest().body("Failed to update seat");
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSeat(@PathVariable UUID id) {
        boolean result = seatService.deleteSeat(id);
        return ResponseEntity.ok(result);
    }
}