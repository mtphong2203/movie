package com.jaf.movietheater.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.room.RoomDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateDTO;
import com.jaf.movietheater.entities.Schedule;
import com.jaf.movietheater.mappers.RoomMapper;
import com.jaf.movietheater.mappers.ScheduleMapper;
import com.jaf.movietheater.mappers.ShowDateMapper;
import com.jaf.movietheater.repository.MovieRepository;
import com.jaf.movietheater.repository.MovieScheduleShowDateRoomRepository;
import com.jaf.movietheater.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ShowDateMapper showDateMapper;
    private final RoomMapper roomMapper;
    private final MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository;
    private final MovieRepository movieRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository, 
    MovieRepository movieRepository, ShowDateMapper showDateMapper, RoomMapper roomMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.showDateMapper = showDateMapper;
        this.roomMapper = roomMapper;
        this.movieScheduleShowDateRoomRepository = movieScheduleShowDateRoomRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<ScheduleMasterDTO> findAll() {
        var schedules = scheduleRepository.findAll();
        
        var scheduleDTOs = schedules.stream().map(schedule -> {

            var scheduleDTO = scheduleMapper.toMasterDTO(schedule);

            return  scheduleDTO;

        }).toList();

        return scheduleDTOs;
    }

    @Override
    public List<ScheduleMasterDTO> findByName(String keyword) {
        Specification<Schedule> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var schedules = scheduleRepository.findAll(specification);

        var scheduleDTOs = schedules.stream().map(schedule -> {

            return  scheduleMapper.toMasterDTO(schedule);

        }).toList();

        return scheduleDTOs;
    }

    @Override
    public Page<ScheduleMasterDTO> findPagination(String keyword, Pageable pageable) {
        Specification<Schedule> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var schedules = scheduleRepository.findAll(specification, pageable);

        var scheduleDTOs = schedules.map(schedule -> {
            var scheduleDTO = scheduleMapper.toMasterDTO(schedule);

            return scheduleDTO;
        });

        return scheduleDTOs;
    }

    @Override
    public ScheduleMasterDTO findById(UUID id) {
        var schedule = scheduleRepository.findById(id).orElse(null);

        if (schedule == null) {
            throw new IllegalArgumentException("schedule is null");
        }

        var scheduleMasterDTO = scheduleMapper.toMasterDTO(schedule);

        return scheduleMasterDTO;
    }

    @Override
    public ScheduleMasterDTO create(ScheduleCreateUpdateDTO scheduleCreateUpdateDTO) {
        // Check null scheduleCreateUpdateDTO
        if (scheduleCreateUpdateDTO == null) {
            throw new IllegalArgumentException("scheduleCreateUpdateDTO is required");
        }

        // Check existing schedule before
        var existingSchedule = scheduleRepository.findByScheduleTime(scheduleCreateUpdateDTO.getScheduleTime());

        if (existingSchedule != null) {
            throw new IllegalArgumentException("Schedule is exsisting");
        }

        // Create
        var schedule = scheduleMapper.toEntity(scheduleCreateUpdateDTO);

        // save
        schedule = scheduleRepository.save(schedule);

        // Convert to ScheduleMasterDTO
        return  scheduleMapper.toMasterDTO(schedule);
    }

    @Override
    public ScheduleMasterDTO update(UUID id, ScheduleCreateUpdateDTO scheduleCreateUpdateDTO) {
        // Check null scheduleCreateUpdateDTO
        if (scheduleCreateUpdateDTO == null) {
            throw new IllegalArgumentException("scheduleCreateUpdateDTO is required");
        }

        // Check updatedSchedule is existing
        var existingUpdatedSchedule = scheduleRepository.findById(id).orElse(null);

        if (existingUpdatedSchedule == null) {
            throw new IllegalArgumentException("schedule is not existing");
        }

        // Check existing schedule before
        var existingSchedule = scheduleRepository.findByScheduleTime(scheduleCreateUpdateDTO.getScheduleTime());

        if (existingSchedule != null && !existingSchedule.getId().equals(id)) {
            throw new IllegalArgumentException("Schedule is exsisting");
        }

        scheduleMapper.updatedEntity(scheduleCreateUpdateDTO, existingUpdatedSchedule);
        existingUpdatedSchedule.setId(id);

        existingUpdatedSchedule = scheduleRepository.save(existingUpdatedSchedule);

        return scheduleMapper.toMasterDTO(existingUpdatedSchedule);
    }

    @Override
    public boolean delete(UUID id) {
        var deletedSchedule = scheduleRepository.findById(id).orElse(null);

        if (deletedSchedule == null) {
            throw new IllegalArgumentException("Schedule not found");
        }

        scheduleRepository.deleteById(id);

        return !scheduleRepository.existsById(id);
    }

    @Override
    public List<ScheduleMasterDTO> findAllScheduleAvailable(ShowDateDTO showDateDTO, RoomDTO cinemaRoomDTO, UUID movieId) {
        var movie = movieRepository.findById(movieId).orElse(null);

        if (movie == null) {
            throw new IllegalArgumentException("Movie is null");
        }

        var duration = movie.getDuration();

        // Set<MovieScheduleShowDateRoom>
        var showDate = showDateMapper.toEntity(showDateDTO);

        var cinemaRoom = roomMapper.toEntity(cinemaRoomDTO);

        // List all<Schedule>
        var schedules = scheduleRepository.findAll();

        var mSSRs = movieScheduleShowDateRoomRepository.findAllMovieScheduleShowDateRoomsByShowDateAndRoom(showDate, cinemaRoom);

        if (mSSRs == null) {
            return schedules.stream().map(scheduleMapper::toMasterDTO).collect(Collectors.toList());
        }
        
        // List<Schedule> from showDateId, RoomId
        var unAvailableSchedules = scheduleRepository.findAllSchedulesByMovieScheduleShowDateRooms(mSSRs);

        // Get all available schedule
        List<Schedule> filteredSchedules = new ArrayList<Schedule>();
        
        filteredSchedules = schedules.stream().filter(schedule -> !unAvailableSchedules.contains(schedule)).collect(Collectors.toList());

        // Sorted unvailable schedule
        var sortedUnAvailableSchedules = unAvailableSchedules.stream().sorted(Comparator.comparing(Schedule::getScheduleTimeNumber)).collect(Collectors.toList());

        // Sorted filtered schedules
        var sortedFilteredSchedules = filteredSchedules.stream().sorted(Comparator.comparing(Schedule::getScheduleTimeNumber)).collect(Collectors.toList());

        List<Schedule> availableSchedules = new ArrayList<Schedule>();

        for (int i = 0; i < sortedFilteredSchedules.size(); i++) {
            Schedule filteredSchedule = sortedFilteredSchedules.get(i);
            int filteredScheduleTimeNumber = filteredSchedule.getScheduleTimeNumber();


            for (int j = 0; j < sortedUnAvailableSchedules.size(); j++) {
                Schedule unavailableSchedule01 = sortedUnAvailableSchedules.get(j);
                Schedule unavailableSchedule02 = sortedUnAvailableSchedules.get(j + 1);
                int unvailableScheduleTimeNumber01 = unavailableSchedule01.getScheduleTimeNumber();
                int unvailableScheduleTimeNumber02 = unavailableSchedule02.getScheduleTimeNumber();

                if (filteredScheduleTimeNumber > unvailableScheduleTimeNumber01 && filteredScheduleTimeNumber < unvailableScheduleTimeNumber02) {
                    var movie1 = movieScheduleShowDateRoomRepository.findMovieByShowDateAndRoomAndSchedule(showDate, cinemaRoom, unavailableSchedule01);
                    int durationMovie1 = movie1.getDuration();
                    
                    if (unvailableScheduleTimeNumber01 + durationMovie1 < filteredScheduleTimeNumber && filteredScheduleTimeNumber + duration < unvailableScheduleTimeNumber02) {
                        availableSchedules.add(filteredSchedule);
                    }
                }
            }
        }

        var scheduleMasterDTOs = availableSchedules.stream().map(availableSchedule -> {
            var scheduleDTO = scheduleMapper.toMasterDTO(availableSchedule);
            return scheduleDTO;
        }).toList();

        return scheduleMasterDTOs;
    }
}
