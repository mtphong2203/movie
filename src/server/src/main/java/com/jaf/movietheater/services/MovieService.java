package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.movie.MovieCreateUpdateDTO;
import com.jaf.movietheater.dtos.movie.MovieDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;

public interface MovieService {
    List<MovieDTO> findAll();

    List<MovieDTO> findByName(String keyword);

    Page<MovieDTO> findPagination(String keyword, Pageable pageable);

    MovieMasterDTO findById(UUID id);

    MovieDTO create(MovieCreateUpdateDTO movieCreateUpdateDTO);

    MovieDTO update(UUID id, MovieCreateUpdateDTO movieCreateUpdateDTO);

    boolean delete(UUID id);
}
