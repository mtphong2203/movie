package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.movie.MovieCreateDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.dtos.movie.MovieUpdateDTO;

public interface MovieService {
    List<MovieMasterDTO> findAll();

    List<MovieMasterDTO> findByName(String keyword);

    Page<MovieMasterDTO> findPagination(String keyword, Pageable pageable);

    MovieMasterDTO findById(UUID id);

    MovieMasterDTO create(MovieCreateDTO movieCreateDTO);

    MovieMasterDTO update(UUID id, MovieUpdateDTO movieUpdateDTO);

    boolean delete(UUID id);
}
