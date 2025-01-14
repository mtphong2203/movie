package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;
import com.jaf.movietheater.entities.Schedule;
import com.jaf.movietheater.mappers.ScheduleMapper;
import com.jaf.movietheater.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
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

}
