package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.showdate.ShowDateCreateUpdateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateMasterDTO;
import com.jaf.movietheater.entities.ShowDate;
import com.jaf.movietheater.mappers.ShowDateMapper;
import com.jaf.movietheater.repository.ShowDateRepository;

@Service
@Transactional
public class ShowDateServiceImpl implements ShowDateService{
    private final ShowDateRepository showDateRepository;
    private final ShowDateMapper showDateMapper;

    public ShowDateServiceImpl(ShowDateRepository showDateRepository, ShowDateMapper showDateMapper) {
        this.showDateRepository = showDateRepository;
        this.showDateMapper = showDateMapper;
    }

    @Override
    public List<ShowDateMasterDTO> findAll() {
        var showDates = showDateRepository.findAll();
        
        var showDateDTOs = showDates.stream().map(showDate -> {

            var showDateDTO = showDateMapper.toMasterDTO(showDate);

            return  showDateDTO;

        }).toList();

        return showDateDTOs;
    }

    @Override
    public List<ShowDateMasterDTO> findByName(String keyword) {
        Specification<ShowDate> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var showDates = showDateRepository.findAll(specification);

        var showDateDTOs = showDates.stream().map(showDate -> {

            return  showDateMapper.toMasterDTO(showDate);

        }).toList();

        return showDateDTOs;
    }

    @Override
    public Page<ShowDateMasterDTO> findPagination(String keyword, Pageable pageable) {
        Specification<ShowDate> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var showDates = showDateRepository.findAll(specification, pageable);

        var showDateDTOs = showDates.map(showDate -> {
            var showDateDTO = showDateMapper.toMasterDTO(showDate);

            return showDateDTO;
        });

        return showDateDTOs;
    }

    @Override
    public ShowDateMasterDTO findById(UUID id) {
        var showDate = showDateRepository.findById(id).orElse(null);

        if (showDate == null) {
            throw new IllegalArgumentException("showDate is null");
        }

        var showDateMasterDTO = showDateMapper.toMasterDTO(showDate);

        return showDateMasterDTO;
    }

    @Override
    public ShowDateMasterDTO create(ShowDateCreateUpdateDTO showDateCreateUpdateDTO) {
        // Check null showDateCreateUpdateDTO
        if (showDateCreateUpdateDTO == null) {
            throw new IllegalArgumentException("showDateCreateUpdateDTO is required");
        }

        // Check existing showDate before
        var existingShowDate = showDateRepository.findByShowDate(showDateCreateUpdateDTO.getShowDate());

        if (existingShowDate != null) {
            throw new IllegalArgumentException("ShowDate is exsisting");
        }

        // Create
        var showDate = showDateMapper.toEntity(showDateCreateUpdateDTO);

        // save
        showDate = showDateRepository.save(showDate);

        // Convert to ShowDateMasterDTO
        return  showDateMapper.toMasterDTO(showDate);
    }

    @Override
    public ShowDateMasterDTO update(UUID id, ShowDateCreateUpdateDTO showDateCreateUpdateDTO) {
        // Check null showDateCreateUpdateDTO
        if (showDateCreateUpdateDTO == null) {
            throw new IllegalArgumentException("showDateCreateUpdateDTO is required");
        }

        // Check updatedShowDate is existing
        var existingUpdatedShowDate = showDateRepository.findById(id).orElse(null);

        if (existingUpdatedShowDate == null) {
            throw new IllegalArgumentException("showDate is not existing");
        }

        // Check existing showDate before
        var existingShowDate = showDateRepository.findByShowDate(showDateCreateUpdateDTO.getShowDate());

        if (existingShowDate != null && !existingShowDate.getId().equals(id)) {
            throw new IllegalArgumentException("ShowDate is exsisting");
        }

        showDateMapper.updatedEntity(showDateCreateUpdateDTO, existingUpdatedShowDate);
        existingUpdatedShowDate.setId(id);

        existingUpdatedShowDate = showDateRepository.save(existingUpdatedShowDate);

        return showDateMapper.toMasterDTO(existingUpdatedShowDate);
    }

    @Override
    public boolean delete(UUID id) {
        var deletedShowDate = showDateRepository.findById(id).orElse(null);

        if (deletedShowDate == null) {
            throw new IllegalArgumentException("ShowDate not found");
        }

        showDateRepository.deleteById(id);

        return !showDateRepository.existsById(id);
    }

}
