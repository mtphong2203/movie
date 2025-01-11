package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.entities.Seat;
import com.jaf.movietheater.repository.RoomRepository;
import com.jaf.movietheater.repository.SeatRepository;

import jakarta.persistence.EntityNotFoundException;

import com.jaf.movietheater.mappers.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<SeatMasterDTO> getAll() {
        return seatRepository.findAll().stream()
                .map(seatMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatMasterDTO getById(UUID id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        return seat != null ? seatMapper.toDTO(seat) : null;
    }

    @Override
    public SeatMasterDTO create(SeatCreateUpdateDTO seatCreateUpdateDTO) {
        Seat seat = seatMapper.toEntity(seatCreateUpdateDTO);
        seat.setRoom(roomRepository.findById(seatCreateUpdateDTO.getRoomId())
                        .orElseThrow(() -> new EntityNotFoundException("Room not found")));
        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toDTO(savedSeat);
    }

    @Override
    public SeatMasterDTO update(UUID id, SeatCreateUpdateDTO seatCreateUpdateDTO) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) {
            return null;
        }
        seatMapper.toEntity(seatCreateUpdateDTO, seat);
        seat.setRoom(roomRepository.findById(seatCreateUpdateDTO.getRoomId())
                        .orElseThrow(() -> new EntityNotFoundException("Room not found")));
        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.toDTO(updatedSeat);
    }

    @Override
    public boolean delete(UUID id) {
        if (seatRepository.existsById(id)) {
            seatRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<SeatMasterDTO> getSeatByRoomId(UUID roomId) {
        return seatRepository.findByRoomId(roomId).stream()
                .map(seatMapper::toDTO)
                .collect(Collectors.toList());
    }
}