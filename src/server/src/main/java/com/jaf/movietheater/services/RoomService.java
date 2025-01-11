package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.room.*;

public interface RoomService {
    List<RoomMasterDTO> getAll();

    RoomMasterDTO getById(UUID id);

    Page<RoomMasterDTO> getAll(String keyword, Pageable pageable);
    //get room by cinema id
    Page<RoomMasterDTO> getAllByCinemaId(UUID cinemaId, Pageable pageable);

    RoomMasterDTO create(RoomCreateUpdateDTO roomDTO);

    RoomMasterDTO update(UUID id, RoomCreateUpdateDTO roomDTO);

    boolean delete(UUID id);
}