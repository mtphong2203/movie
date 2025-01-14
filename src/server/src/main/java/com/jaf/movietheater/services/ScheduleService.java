package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;

public interface ScheduleService {
    List<ScheduleMasterDTO> findAll();

    List<ScheduleMasterDTO> findByName(String keyword);

    Page<ScheduleMasterDTO> findPagination(String keyword, Pageable pageable);

    ScheduleMasterDTO findById(UUID id);

    ScheduleMasterDTO create(ScheduleCreateUpdateDTO scheduleCreateUpdateDTO);

    ScheduleMasterDTO update(UUID id, ScheduleCreateUpdateDTO scheduleCreateUpdateDTO);

    boolean delete(UUID id);
}
