package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.room.RoomDTO;
import com.jaf.movietheater.dtos.room.RoomMasterDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateDTO;

public interface ScheduleService {
    List<ScheduleMasterDTO> findAll();

    List<ScheduleMasterDTO> findByName(String keyword);

    Page<ScheduleMasterDTO> findPagination(String keyword, Pageable pageable);

    ScheduleMasterDTO findById(UUID id);

    ScheduleMasterDTO create(ScheduleCreateUpdateDTO scheduleCreateUpdateDTO);

    ScheduleMasterDTO update(UUID id, ScheduleCreateUpdateDTO scheduleCreateUpdateDTO);

    boolean delete(UUID id);

    List<ScheduleMasterDTO> findAllScheduleAvailable(ShowDateDTO showDate, RoomDTO cinemaRoom, UUID movieId);
}
