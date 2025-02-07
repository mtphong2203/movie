package com.jaf.movietheater.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.movie.MovieCreateDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.dtos.movie.MovieUpdateDTO;
import com.jaf.movietheater.dtos.movie.ShowDateRoomSchduleDTO;
import com.jaf.movietheater.entities.Movie;
import com.jaf.movietheater.entities.MovieScheduleShowDateRoom;
import com.jaf.movietheater.entities.MovieScheduleShowDateRoomId;
import com.jaf.movietheater.entities.MovieType;
import com.jaf.movietheater.mappers.MovieMapper;
import com.jaf.movietheater.repository.MovieRepository;
import com.jaf.movietheater.repository.MovieScheduleShowDateRoomRepository;
import com.jaf.movietheater.repository.MovieTypeRepository;
import com.jaf.movietheater.repository.RoomRepository;
import com.jaf.movietheater.repository.ScheduleRepository;
import com.jaf.movietheater.repository.ShowDateRepository;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieTypeRepository movieTypeRepository;
    private final ShowDateRepository showDateRepository;
    private final RoomRepository roomRepository;
    private final ScheduleRepository scheduleRepository;
    private final MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, MovieTypeRepository movieTypeRepository,
                            ShowDateRepository showDateRepository, RoomRepository roomRepository, ScheduleRepository scheduleRepository, MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.movieTypeRepository = movieTypeRepository;
        this.showDateRepository = showDateRepository;
        this.roomRepository = roomRepository;
        this.scheduleRepository = scheduleRepository;
        this.movieScheduleShowDateRoomRepository = movieScheduleShowDateRoomRepository;
    }

    @Override
    public List<MovieMasterDTO> findAll() {
        var movies = movieRepository.findAll();
        
        var movieDTOs = movies.stream().map(movie -> {

            var movieDTO = movieMapper.toMasterDTO(movie);

            return  movieDTO;

        }).toList();

        return movieDTOs;
    }

    @Override
    public List<MovieMasterDTO> findByName(String keyword) {
        Specification<Movie> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movies = movieRepository.findAll(specification);

        var movieDTOs = movies.stream().map(movie -> {

            return  movieMapper.toMasterDTO(movie);

        }).toList();

        return movieDTOs;
    }

    @Override
    public Page<MovieMasterDTO> findPagination(String keyword, Pageable pageable) {
        Specification<Movie> specification = (r, q, cb) -> {
            if (keyword == null) {
                return null;
            }
            return cb.like(r.get("name"), "%" + keyword + "%");
        };

        var movies = movieRepository.findAll(specification, pageable);

        var movieDTOs = movies.map(movie -> {
            var movieDTO = movieMapper.toMasterDTO(movie);

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
    public MovieMasterDTO create(MovieCreateDTO movieCreateDTO) {
        // Check null movieCreateUpdateDTO
        if (movieCreateDTO == null) {
            throw new IllegalArgumentException("movieCreateUpdateDTO is required");
        }

        // Check existing movie before
        var existingMovie = movieRepository.findByName(movieCreateDTO.getName());

        if (existingMovie != null) {
            throw new IllegalArgumentException("Movie is exsisting");
        }

        // Create
        var movie = movieMapper.toEntity(movieCreateDTO);

        // Get all movie types by Id
        var movieTypes = movieTypeRepository.findAllByIdIn(movieCreateDTO.getMovieTypeIds());

        if (movieTypes == null) {
            throw new IllegalArgumentException("movieTypes is not existing");
        }

        movie.setMovieTypes(movieTypes);

        // save
        movie = movieRepository.save(movie);

        // Convert to MovieMasterDTO
        return  movieMapper.toMasterDTO(movie);
    }

    @Override
    public MovieMasterDTO update(UUID id, MovieUpdateDTO movieUpdateDTO) {
        // Check null movieCreateUpdateDTO
        if (movieUpdateDTO == null) {
            throw new IllegalArgumentException("movieCreateUpdateDTO is required");
        }

        // Check updatedMovie is existing
        var existingUpdatedMovie = movieRepository.findById(id).orElse(null);

        if (existingUpdatedMovie == null) {
            throw new IllegalArgumentException("movie is not existing");
        }

        // Check existing movie before
        var existingMovie = movieRepository.findByName(movieUpdateDTO.getName());

        if (existingMovie != null && !existingMovie.getId().equals(id)) {
            throw new IllegalArgumentException("Movie is exsisting");
        }

        movieMapper.updatedEntity(movieUpdateDTO, existingUpdatedMovie);
        existingUpdatedMovie.setId(id);

        existingUpdatedMovie = movieRepository.save(existingUpdatedMovie);

        // Update movieTypes
        var movieTypes = movieTypeRepository.findAllByIdIn(movieUpdateDTO.getMovieTypeIds());

        if (movieTypes == null || movieTypes.isEmpty()) {
            throw new IllegalArgumentException("MovieTypes is null");
        }

        existingUpdatedMovie.setMovieTypes(movieTypes);

        // Link ShowDate, Room, Schedule and Movie
        updateMovieScheduleShowDateRooms(existingUpdatedMovie, movieUpdateDTO.getShowDateRoomSchdules());

        return movieMapper.toMasterDTO(existingUpdatedMovie);
    }

    private void updateMovieScheduleShowDateRooms(Movie movie, Set<ShowDateRoomSchduleDTO> showDateRoomSchdules) {
        if (showDateRoomSchdules == null || showDateRoomSchdules.isEmpty()) {
            return;
        }

        for (ShowDateRoomSchduleDTO dto : showDateRoomSchdules) {
            // Fetch showDate
            var showDate = showDateRepository.findById(dto.getShowDateId()).orElseThrow(() -> new IllegalArgumentException("ShowDate not found"));

            // Fetch room
            var room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new IllegalArgumentException("Room not found"));

            // Fetch schedule
            var schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new IllegalArgumentException("Scheduel not found"));

            // Set id for MovieScheduleShowDateRoom
            var id = new MovieScheduleShowDateRoomId(dto.getShowDateId(), dto.getRoomId(), dto.getScheduleId());

            // Save relationship in MovieScheduleShowDateRoom
            MovieScheduleShowDateRoom mssr = new MovieScheduleShowDateRoom();
            mssr.setId(id);
            mssr.setShowDate(showDate);
            mssr.setRoom(room);
            mssr.setSchedule(schedule);
            mssr.setMovie(movie);

            movieScheduleShowDateRoomRepository.save(mssr);
        }
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
