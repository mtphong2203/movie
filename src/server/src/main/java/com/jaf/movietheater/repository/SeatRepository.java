package com.jaf.movietheater.repository;

import com.jaf.movietheater.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID>, JpaSpecificationExecutor<Seat> {
    List<Seat> findByRoomId(UUID roomId);
}