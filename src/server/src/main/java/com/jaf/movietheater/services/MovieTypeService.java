package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.movietype.MovieTypeCreateUpdateDTO;
import com.jaf.movietheater.dtos.movietype.MovieTypeMasterDTO;

public interface MovieTypeService {
    List<MovieTypeMasterDTO> findAll();

    List<MovieTypeMasterDTO> findByName(String keyword);

    Page<MovieTypeMasterDTO> findPagination(String keyword, Pageable pageable);

    MovieTypeMasterDTO findById(UUID id);

    MovieTypeMasterDTO create(MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO);

    MovieTypeMasterDTO update(UUID id, MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO);

    boolean delete(UUID id);
}
