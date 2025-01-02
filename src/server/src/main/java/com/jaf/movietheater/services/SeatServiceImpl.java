package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.entities.Seat;
import com.jaf.movietheater.repository.SeatRepository;
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
    private SeatMapper seatMapper;

    @Override
    public List<SeatDTO> getAllSeats() {
        return seatRepository.findAll().stream()
                .map(seatMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDTO getSeatById(UUID id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        return seat != null ? seatMapper.toDTO(seat) : null;
    }

    @Override
    public SeatDTO createSeat(SeatCreateUpdateDTO seatCreateUpdateDTO) {
        Seat seat = seatMapper.toEntity(seatCreateUpdateDTO);
        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toDTO(savedSeat);
    }

    @Override
    public SeatDTO updateSeat(UUID id, SeatCreateUpdateDTO seatCreateUpdateDTO) {
        Seat seat = seatRepository.findById(id).orElse(null);
        if (seat == null) {
            return null;
        }
        seatMapper.toEntity(seatCreateUpdateDTO, seat);
        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.toDTO(updatedSeat);
    }

    @Override
    public boolean deleteSeat(UUID id) {
        if (seatRepository.existsById(id)) {
            seatRepository.deleteById(id);
            return true;
        }
        return false;
    }
}