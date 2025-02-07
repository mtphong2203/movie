package com.jaf.movietheater.repository;

import com.jaf.movietheater.entities.MovieScheduleShowDateRoom;
import com.jaf.movietheater.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
    List<Room> findAllRoomsByMovieScheduleShowDateRooms(Set<MovieScheduleShowDateRoom> movieScheduleShowDateRooms);
}