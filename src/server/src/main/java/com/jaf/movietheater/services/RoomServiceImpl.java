package com.jaf.movietheater.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.room.RoomCreateUpdateDTO;
import com.jaf.movietheater.dtos.room.RoomMasterDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateDTO;
import com.jaf.movietheater.entities.Cinema;
import com.jaf.movietheater.entities.MovieScheduleShowDateRoom;
import com.jaf.movietheater.entities.Room;
import com.jaf.movietheater.exceptions.ResourceNotFoundException;
import com.jaf.movietheater.mappers.RoomMapper;
import com.jaf.movietheater.mappers.ShowDateMapper;
import com.jaf.movietheater.repository.CinemaRepository;
import com.jaf.movietheater.repository.MovieScheduleShowDateRoomRepository;
import com.jaf.movietheater.repository.RoomRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private ShowDateMapper showDateMapper;

    @Autowired
    private MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public List<RoomMasterDTO> getAll() {
        var rooms = roomRepository.findAll();
        return rooms.stream().map(roomMapper::toMasterDTO).collect(Collectors.toList());
    }

    @Override
    public RoomMasterDTO getById(UUID id) {
        var room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return roomMapper.toMasterDTO(room);
    }


    @Override
    public RoomMasterDTO create(RoomCreateUpdateDTO roomDTO) {
        Cinema cinema = cinemaRepository.findById(roomDTO.getCinemaId()).orElseThrow(() -> new ResourceNotFoundException("Cinema not found"));
        var room = roomMapper.toEntity(roomDTO);
        room = roomRepository.save(room);
        room.setCinema(cinema);
        return roomMapper.toMasterDTO(room);
    }

    @Override
    public RoomMasterDTO update(UUID id, RoomCreateUpdateDTO roomDTO) {
        var room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomMapper.toEntity(roomDTO, room);
        room = roomRepository.save(room);
        return roomMapper.toMasterDTO(room);
    }

    @Override
    public boolean delete(UUID id) {
        var room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.delete(room);
        return !roomRepository.existsById(id);
    }

    @Override
    public Page<RoomMasterDTO> getAll(String keyword, Pageable pageable) {
        Specification<Room> specification = (root, query, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            return namePredicate;
        };

        var rooms = roomRepository.findAll(specification, pageable);

        var roomMasterDTOs = rooms.map(room -> {
            var roomMasterDTO = roomMapper.toMasterDTO(room);
            return roomMasterDTO;
        });

        return roomMasterDTOs;
    }

    @Override
    public Page<RoomMasterDTO> getAllByCinemaId(UUID cinemaId, Pageable pageable) {
        Specification<Room> specification = (root, query, criteriaBuilder) -> {
            Predicate cinemaPredicate = criteriaBuilder.equal(root.get("cinema").get("id"), cinemaId);

            return cinemaPredicate;
        };

        var rooms = roomRepository.findAll(specification, pageable);

        var roomMasterDTOs = rooms.map(room -> {
            var roomMasterDTO = roomMapper.toMasterDTO(room);
            return roomMasterDTO;
        });

        return roomMasterDTOs;
    }

    @Override
    public List<RoomMasterDTO> getAllAvailableRooms(ShowDateDTO showDateDTO, UUID movieId) {
        var showDate = showDateMapper.toEntity(showDateDTO);

        var rooms = roomRepository.findAll();

        var mSSRs = movieScheduleShowDateRoomRepository.findAllMovieScheduleShowDateRoomsByShowDate(showDate);

        if (mSSRs == null || mSSRs.isEmpty()) {
            return rooms.stream().map(roomMapper::toMasterDTO).collect(Collectors.toList());
        }

        List<Room> availableRooms = new ArrayList<Room>();
        
        var chosenRooms = roomRepository.findAllRoomsByMovieScheduleShowDateRooms(mSSRs);

        var filteredRooms = chosenRooms.stream().filter((Room room) -> {

            var roomDTO = roomMapper.toDTO(room);

            var schedules = scheduleService.findAllScheduleAvailable(showDateDTO, roomDTO, movieId);

            return schedules != null && !schedules.isEmpty();

        }).toList();

        if (filteredRooms != null) {
            availableRooms.addAll(filteredRooms);
        }

        var unChosenRooms = rooms.stream().filter(room -> !chosenRooms.contains(room)).collect(Collectors.toList());

        if (unChosenRooms != null) {
            availableRooms.addAll(unChosenRooms);
        }

        return availableRooms.stream().map(roomMapper::toMasterDTO).collect(Collectors.toList());
    }
}