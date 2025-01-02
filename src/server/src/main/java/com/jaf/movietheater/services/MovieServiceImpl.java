package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.movie.MovieCreateUpdateDTO;
import com.jaf.movietheater.dtos.movie.MovieDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.entities.Movie;
import com.jaf.movietheater.mappers.MovieMapper;
import com.jaf.movietheater.repository.MovieRepository;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> findAll() {
        var movies = movieRepository.findAll();
        
        var movieDTOs = movies.stream().map(movie -> {

            var movieDTO = movieMapper.toDTO(movie);

            return  movieDTO;

        }).toList();

        return movieDTOs;
    }

    @Override
    public List<MovieDTO> findByName(String keyword) {
        Specification<Movie> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movies = movieRepository.findAll(specification);

        var movieDTOs = movies.stream().map(movie -> {

            return  movieMapper.toDTO(movie);

        }).toList();

        return movieDTOs;
    }

    @Override
    public Page<MovieDTO> findPagination(String keyword, Pageable pageable) {
        Specification<Movie> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movies = movieRepository.findAll(specification, pageable);

        var movieDTOs = movies.map(movie -> {
            var movieDTO = movieMapper.toDTO(movie);

            return movieDTO;
        });

        return movieDTOs;
    }

    @Override
    public MovieMasterDTO findById(UUID id) {
        var movie = movieRepository.findById(id).orElse(null);

        if (movie == null) {
            throw new IllegalArgumentException("movie is null");
        }

        var movieMasterDTO = movieMapper.toMasterDTO(movie);

        return movieMasterDTO;
    }

    @Override
    public MovieDTO create(MovieCreateUpdateDTO movieCreateUpdateDTO) {
        // Check null movieCreateUpdateDTO
        if (movieCreateUpdateDTO == null) {
            throw new IllegalArgumentException("movieCreateUpdateDTO is required");
        }

        // Check existing movie before
        var existingMovie = movieRepository.findByName(movieCreateUpdateDTO.getName());

        if (existingMovie != null) {
            throw new IllegalArgumentException("Movie is exsisting");
        }

        // Create
        var movie = movieMapper.toEntity(movieCreateUpdateDTO);

        // save
        movie = movieRepository.save(movie);

        // Convert to MovieDTO
        return  movieMapper.toDTO(movie);
    }

    @Override
    public MovieDTO update(UUID id, MovieCreateUpdateDTO movieCreateUpdateDTO) {
        // Check null movieCreateUpdateDTO
        if (movieCreateUpdateDTO == null) {
            throw new IllegalArgumentException("movieCreateUpdateDTO is required");
        }

        // Check updatedMovie is existing
        var existingUpdatedMovie = movieRepository.findById(id).orElse(null);

        if (existingUpdatedMovie == null) {
            throw new IllegalArgumentException("movie is not existing");
        }

        // Check existing movie before
        var existingMovie = movieRepository.findByName(movieCreateUpdateDTO.getName());

        if (existingMovie != null && !existingMovie.getId().equals(id)) {
            throw new IllegalArgumentException("Movie is exsisting");
        }

        movieMapper.updatedEntity(movieCreateUpdateDTO, existingUpdatedMovie);
        existingUpdatedMovie.setId(id);

        existingUpdatedMovie = movieRepository.save(existingUpdatedMovie);

        return movieMapper.toDTO(existingUpdatedMovie);
    }

    @Override
    public boolean delete(UUID id) {
        var deletedMovie = movieRepository.findById(id).orElse(null);

        if (deletedMovie == null) {
            throw new IllegalArgumentException("Movie not found");
        }

        movieRepository.deleteById(id);

        return !movieRepository.existsById(id);
    }

}
