package com.jaf.movietheater.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jaf.movietheater.entities.MovieScheduleShowDateRoom;
import com.jaf.movietheater.entities.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule>{
    Schedule findByScheduleTime(String scheduleTime);
    List<Schedule> findAllSchedulesByMovieScheduleShowDateRooms(Set<MovieScheduleShowDateRoom> movieScheduleShowDateRooms);
}
