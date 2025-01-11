package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.entities.Seat;
import com.jaf.movietheater.entities.SeatStatus;
import com.jaf.movietheater.entities.SeatType;
import com.jaf.movietheater.repository.RoomRepository;
import com.jaf.movietheater.repository.SeatRepository;

import jakarta.persistence.EntityNotFoundException;


import jakarta.persistence.EntityNotFoundException;

import com.jaf.movietheater.mappers.SeatMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public void importSeatsFromExcel(MultipartFile file, UUID roomId) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
        Sheet sheet = workbook.getSheetAt(0);
        List<Seat> seats = new ArrayList<>();
        
        for (Row row : sheet) {
            int rowNum = row.getRowNum() + 1;
            
            for (Cell cell : row) {
                int colNum = cell.getColumnIndex();
                String type = cell.getStringCellValue().trim();
                
                if (!type.isEmpty()) {
                    Seat seat = new Seat();
                    seat.setRoom(roomRepository.findById(roomId)
                        .orElseThrow(() -> new EntityNotFoundException("Room not found")));
                    seat.setActive(true);
                    seat.setStatus(SeatStatus.AVAILABLE);
                    seat.setType(SeatType.valueOf(type));
                    
                    char colLetter = (char) ('A' + colNum);
                    seat.setSeat_row(String.valueOf(colLetter));
                    seat.setSeat_column(String.valueOf(rowNum));
                    
                    seats.add(seat);
                }
            }
        }
        
        seatRepository.saveAll(seats);
        
    } catch (IOException e) {
        throw new RuntimeException("Failed to import Excel file: " + e.getMessage());
    }
    }
}