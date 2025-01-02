package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.rooms.*;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<RoomDTO> getAllRooms(); 
    
    RoomDTO getRoomById(UUID id);

    RoomDTO createRoom(RoomCreateUpdateDTO roomCreateUpdateDTO);

    RoomDTO updateRoom(UUID id, RoomCreateUpdateDTO roomCreateUpdateDTO);

    boolean deleteRoom(UUID id);
}