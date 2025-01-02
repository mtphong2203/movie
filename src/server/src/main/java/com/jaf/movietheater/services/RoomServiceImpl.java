package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.rooms.*;
import com.jaf.movietheater.entities.Room;
import com.jaf.movietheater.repository.RoomRepository;
import com.jaf.movietheater.mappers.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(UUID id) {
        Room room = roomRepository.findById(id).orElse(null);
        return room != null ? roomMapper.toDTO(room) : null;
    }

    @Override
    public RoomDTO createRoom(RoomCreateUpdateDTO roomCreateUpdateDTO) {
        Room room = roomMapper.toEntity(roomCreateUpdateDTO);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDTO(savedRoom);
    }

    @Override
    public RoomDTO updateRoom(UUID id, RoomCreateUpdateDTO roomCreateUpdateDTO) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            return null;
        }
        roomMapper.toEntity(roomCreateUpdateDTO, room);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toDTO(updatedRoom);
    }

    @Override
    public boolean deleteRoom(UUID id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}