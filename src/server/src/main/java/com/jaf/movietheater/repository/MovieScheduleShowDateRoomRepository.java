package com.jaf.movietheater.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jaf.movietheater.entities.Movie;
import com.jaf.movietheater.entities.MovieScheduleShowDateRoom;
import com.jaf.movietheater.entities.Room;
import com.jaf.movietheater.entities.Schedule;
import com.jaf.movietheater.entities.ShowDate;

public interface MovieScheduleShowDateRoomRepository extends JpaRepository<MovieScheduleShowDateRoom, UUID>, JpaSpecificationExecutor<MovieScheduleShowDateRoom> {
    Set<MovieScheduleShowDateRoom> findAllMovieScheduleShowDateRoomsByShowDateAndRoom(ShowDate showDate, Room cinemaRoom);
    Set<MovieScheduleShowDateRoom> findAllMovieScheduleShowDateRoomsByShowDate(ShowDate showDate);
    Movie findMovieByShowDateAndRoomAndSchedule(ShowDate showDate, Room cinemaRoom, Schedule schedule);
}
