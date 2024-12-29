package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.cinema.CinemaCreateUpdateDTO;
import com.jaf.movietheater.dtos.cinema.CinemaMasterDTO;
import com.jaf.movietheater.entities.Cinema;
import com.jaf.movietheater.mappers.CinemaMapper;
import com.jaf.movietheater.repository.CinemaRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
    private CinemaMapper cinemaMapper;

    @Override
    public List<CinemaMasterDTO> getAll() {
        var cinemas = cinemaRepository.findAll();

        var cinemaMasterDTOs = cinemas.stream().map(cinema -> {
            var cinemaMasterDTO = cinemaMapper.toMasterDTO(cinema);
            return cinemaMasterDTO;
        }).toList();

        return cinemaMasterDTOs;
    }

    @Override
    public Page<CinemaMasterDTO> getAll(String keyword, Pageable pageable) {
        Specification<Cinema> specification = (root, query, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate locationPredicate = criteriaBuilder.like(root.get("location"), "%" + keyword + "%");

            return criteriaBuilder.or(namePredicate, locationPredicate);
        };

        var cinemas = cinemaRepository.findAll(specification, pageable);

        var cinemaMasterDTOs = cinemas.map(cinema -> {
            var cinemaMasterDTO = cinemaMapper.toMasterDTO(cinema);
            return cinemaMasterDTO;
        });

        return cinemaMasterDTOs;
    }

    @Override
    public CinemaMasterDTO getById(UUID id) {
        var cinema = cinemaRepository.findById(id).orElse(null);

        if (cinema == null) {
            return null;
        }

        var cinemaMasterDTO = cinemaMapper.toMasterDTO(cinema);

        return cinemaMasterDTO;
    }

    @Override
    public CinemaMasterDTO create(CinemaCreateUpdateDTO cinemaCreateUpdateDTO) {
        if (cinemaCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Cinema is required");
        }

        var cinema = cinemaMapper.toEntity(cinemaCreateUpdateDTO);
        
        cinema = cinemaRepository.save(cinema);

        var cinemaMasterDTO = cinemaMapper.toMasterDTO(cinema);

        return cinemaMasterDTO;
    }

    @Override
    public CinemaMasterDTO update(UUID id, CinemaCreateUpdateDTO cinemaCreateUpdateDTO) {
        if (cinemaCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Cinema is required");
        }

        var cinema = cinemaRepository.findById(id).orElse(null);

        if (cinema == null) {
            return null;
        }

        cinema = cinemaMapper.toEntity(cinemaCreateUpdateDTO, cinema);

        cinema = cinemaRepository.save(cinema);

        var cinemaMasterDTO = cinemaMapper.toMasterDTO(cinema);

        return cinemaMasterDTO;
    }

    @Override
    public boolean deleteById(UUID id) {
        var cinema = cinemaRepository.findById(id).orElse(null);

        if (cinema == null) {
            return false;
        }

        cinemaRepository.delete(cinema);

        return true;
    }

}
