package com.jaf.movietheater.services;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.room.RoomMasterDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateCreateUpdateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateMasterDTO;
import com.jaf.movietheater.entities.Room;
import com.jaf.movietheater.entities.ShowDate;
import com.jaf.movietheater.mappers.ShowDateMapper;
import com.jaf.movietheater.repository.MovieRepository;
import com.jaf.movietheater.repository.MovieScheduleShowDateRoomRepository;
import com.jaf.movietheater.repository.ShowDateRepository;

@Service
@Transactional
public class ShowDateServiceImpl implements ShowDateService{
    private final ShowDateRepository showDateRepository;
    private final ShowDateMapper showDateMapper;
    private final MovieRepository movieRepository;
    private final MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository;
    private final RoomService roomService;

    public ShowDateServiceImpl(ShowDateRepository showDateRepository, ShowDateMapper showDateMapper, MovieRepository movieRepository, MovieScheduleShowDateRoomRepository movieScheduleShowDateRoomRepository, RoomService roomService) {
        this.showDateRepository = showDateRepository;
        this.showDateMapper = showDateMapper;
        this.movieRepository = movieRepository;
        this.movieScheduleShowDateRoomRepository = movieScheduleShowDateRoomRepository;
        this.roomService = roomService;
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

    @Override
    public List<ShowDateMasterDTO> getAllAvailableDates(UUID movieId) {
        var movie = movieRepository.findById(movieId).orElse(null);

        if (movie == null) {
            throw new IllegalArgumentException("Movie is not Exist");
        }

        var fromDate = movie.getFromDate();

        var toDate = movie.getToDate();

        var showDates = showDateRepository.findByShowDateBetween(fromDate, toDate);

        Set<LocalDate> existingShowDateSet = showDates.stream().map(ShowDate::getShowDate).collect(Collectors.toSet());

        // Create only missing ShowDates
        List<ShowDate> newShowDates = new ArrayList<>();

        LocalDate current = fromDate;

        while (!current.isAfter(toDate)) {
            if (!existingShowDateSet.contains(current)) { // Only add missing dates
                ShowDateCreateUpdateDTO showDateCreateUpdateDTO = new ShowDateCreateUpdateDTO();
                // Get the day of the week (Monday, Tuesday, etc.)
                String dayName = current.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                showDateCreateUpdateDTO.setDateName(dayName); // Set the generated dateName
                showDateCreateUpdateDTO.setShowDate(current);
                var newShowDate = showDateMapper.toEntity(showDateCreateUpdateDTO);
                newShowDates.add(newShowDate);
            }

            current = current.plusDays(1);
        }
        
        if (!newShowDates.isEmpty()) {
            showDateRepository.saveAll(newShowDates); // Save only new ShowDates
            showDates.addAll(newShowDates);
        }
        
        // Query all ShowDates again (now complete)
        var allShowDates = showDateRepository.findByShowDateBetween(fromDate, toDate);

        var availableShowDates = allShowDates.stream().filter(showDate -> {

            var showDateDTO = showDateMapper.toDTO(showDate);
            
            List<RoomMasterDTO> availableRooms = new ArrayList<RoomMasterDTO>();
            availableRooms = roomService.getAllAvailableRooms(showDateDTO, movieId);

            return availableRooms != null && !availableRooms.isEmpty();
        }).toList();

        return availableShowDates.stream().map(showDateMapper::toMasterDTO).collect(Collectors.toList());
    }

}