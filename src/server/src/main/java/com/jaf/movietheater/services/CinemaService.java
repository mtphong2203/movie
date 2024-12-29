package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.cinema.CinemaCreateUpdateDTO;
import com.jaf.movietheater.dtos.cinema.CinemaMasterDTO;

public interface CinemaService {
    List<CinemaMasterDTO> getAll();

    Page<CinemaMasterDTO> getAll(String keyword, Pageable pageable);

    CinemaMasterDTO getById(UUID id);

    CinemaMasterDTO create(CinemaCreateUpdateDTO cinemaCreateUpdateDTO);

    CinemaMasterDTO update(UUID id, CinemaCreateUpdateDTO cinemaCreateUpdateDTO);

    boolean deleteById(UUID id);
}
