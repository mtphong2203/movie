package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.seats.*;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<SeatDTO> getAllSeats();
    SeatDTO getSeatById(UUID id);
    SeatDTO createSeat(SeatCreateUpdateDTO seatCreateUpdateDTO);
    SeatDTO updateSeat(UUID id, SeatCreateUpdateDTO seatCreateUpdateDTO);
    boolean deleteSeat(UUID id);
}