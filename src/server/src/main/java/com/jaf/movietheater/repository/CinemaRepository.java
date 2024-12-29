package com.jaf.movietheater.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jaf.movietheater.entities.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, UUID>, JpaSpecificationExecutor<Cinema> {
    Cinema findByName(String name);

}
