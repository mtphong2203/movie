package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.showdate.ShowDateCreateUpdateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateMasterDTO;

public interface ShowDateService {
    List<ShowDateMasterDTO> findAll();

    List<ShowDateMasterDTO> findByName(String keyword);

    Page<ShowDateMasterDTO> findPagination(String keyword, Pageable pageable);

    ShowDateMasterDTO findById(UUID id);

    ShowDateMasterDTO create(ShowDateCreateUpdateDTO showDateCreateUpdateDTO);

    ShowDateMasterDTO update(UUID id, ShowDateCreateUpdateDTO showDateCreateUpdateDTO);

    boolean delete(UUID id);

    List<ShowDateMasterDTO> getAllAvailableDates(UUID movieId);
}
