package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.movietype.MovieTypeCreateUpdateDTO;
import com.jaf.movietheater.dtos.movietype.MovieTypeMasterDTO;
import com.jaf.movietheater.entities.MovieType;
import com.jaf.movietheater.mappers.MovieTypeMapper;
import com.jaf.movietheater.repository.MovieTypeRepository;

@Service
@Transactional
public class MovieTypeServiceImpl implements MovieTypeService{
    private final MovieTypeRepository movieTypeRepository;
    private final MovieTypeMapper movieTypeMapper;

    public MovieTypeServiceImpl(MovieTypeRepository movieTypeRepository, MovieTypeMapper movieTypeMapper) {
        this.movieTypeRepository = movieTypeRepository;
        this.movieTypeMapper = movieTypeMapper;
    }

    @Override
    public List<MovieTypeMasterDTO> findAll() {
        var movieTypes = movieTypeRepository.findAll();
        
        var movieTypeDTOs = movieTypes.stream().map(movieType -> {

            var movieTypeDTO = movieTypeMapper.toMasterDTO(movieType);

            return  movieTypeDTO;

        }).toList();

        return movieTypeDTOs;
    }

    @Override
    public List<MovieTypeMasterDTO> findByName(String keyword) {
        Specification<MovieType> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movieTypes = movieTypeRepository.findAll(specification);

        var movieTypeDTOs = movieTypes.stream().map(movieType -> {

            return  movieTypeMapper.toMasterDTO(movieType);

        }).toList();

        return movieTypeDTOs;
    }

    @Override
    public Page<MovieTypeMasterDTO> findPagination(String keyword, Pageable pageable) {
        Specification<MovieType> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movieTypes = movieTypeRepository.findAll(specification, pageable);

        var movieTypeDTOs = movieTypes.map(movieType -> {
            var movieTypeDTO = movieTypeMapper.toMasterDTO(movieType);

            return movieTypeDTO;
        });

        return movieTypeDTOs;
    }

    @Override
    public MovieTypeMasterDTO findById(UUID id) {
        var movieType = movieTypeRepository.findById(id).orElse(null);

        if (movieType == null) {
            throw new IllegalArgumentException("movieType is null");
        }

        var movieTypeMasterDTO = movieTypeMapper.toMasterDTO(movieType);

        return movieTypeMasterDTO;
    }

    @Override
    public MovieTypeMasterDTO create(MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO) {
        // Check null movieTypeCreateUpdateDTO
        if (movieTypeCreateUpdateDTO == null) {
            throw new IllegalArgumentException("movieTypeCreateUpdateDTO is required");
        }

        // Check existing movieType before
        var existingMovieType = movieTypeRepository.findByTypeName(movieTypeCreateUpdateDTO.getTypeName());

        if (existingMovieType != null) {
            throw new IllegalArgumentException("MovieType is exsisting");
        }

        // Create
        var movieType = movieTypeMapper.toEntity(movieTypeCreateUpdateDTO);

        // save
        movieType = movieTypeRepository.save(movieType);

        // Convert to MovieTypeMasterDTO
        return  movieTypeMapper.toMasterDTO(movieType);
    }

    @Override
    public MovieTypeMasterDTO update(UUID id, MovieTypeCreateUpdateDTO movieTypeCreateUpdateDTO) {
        // Check null movieTypeCreateUpdateDTO
        if (movieTypeCreateUpdateDTO == null) {
            throw new IllegalArgumentException("movieTypeCreateUpdateDTO is required");
        }

        // Check updatedMovieType is existing
        var existingUpdatedMovieType = movieTypeRepository.findById(id).orElse(null);

        if (existingUpdatedMovieType == null) {
            throw new IllegalArgumentException("movieType is not existing");
        }

        // Check existing movieType before
        var existingMovieType = movieTypeRepository.findByTypeName(movieTypeCreateUpdateDTO.getTypeName());

        if (existingMovieType != null && !existingMovieType.getId().equals(id)) {
            throw new IllegalArgumentException("MovieType is exsisting");
        }

        movieTypeMapper.updatedEntity(movieTypeCreateUpdateDTO, existingUpdatedMovieType);
        existingUpdatedMovieType.setId(id);

        existingUpdatedMovieType = movieTypeRepository.save(existingUpdatedMovieType);

        return movieTypeMapper.toMasterDTO(existingUpdatedMovieType);
    }

    @Override
    public boolean delete(UUID id) {
        var deletedMovieType = movieTypeRepository.findById(id).orElse(null);

        if (deletedMovieType == null) {
            throw new IllegalArgumentException("MovieType not found");
        }

        movieTypeRepository.deleteById(id);

        return !movieTypeRepository.existsById(id);
    }

}
