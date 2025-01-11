package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.seats.*;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<SeatMasterDTO> getAll();

    SeatMasterDTO getById(UUID id);

    List<SeatMasterDTO> getSeatByRoomId(UUID roomId);

    SeatMasterDTO create(SeatCreateUpdateDTO seatCreateUpdateDTO);

    SeatMasterDTO update(UUID id, SeatCreateUpdateDTO seatCreateUpdateDTO);

    boolean delete(UUID id);
}