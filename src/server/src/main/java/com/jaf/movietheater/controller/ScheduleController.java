package com.jaf.movietheater.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jaf.movietheater.dtos.room.RoomDTO;
import com.jaf.movietheater.dtos.room.RoomMasterDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateDTO;
import com.jaf.movietheater.mappers.CustomPagedResponse;
import com.jaf.movietheater.services.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/schedules")
@Tag(name = "Schedules", description = "APIs for managing schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final PagedResourcesAssembler<ScheduleMasterDTO> pagedResourcesAssembler;

    public ScheduleController(ScheduleService scheduleService, PagedResourcesAssembler<ScheduleMasterDTO> pagedResourcesAssembler) {
        this.scheduleService = scheduleService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    // get all
    @GetMapping
    @Operation(summary = "Get all schedules")
    @ApiResponse(responseCode = "200", description = "Return all schedules")
    public ResponseEntity<List<ScheduleMasterDTO>> getAll() {
        var scheduleDTOs = scheduleService.findAll();
        return ResponseEntity.ok(scheduleDTOs);
    }

    // get by name
    @GetMapping("/searchByName")
    @Operation(summary = "Search schedule by schedule name")
    @ApiResponse(responseCode = "200", description = "Return schedules that match the schedule name")
    public ResponseEntity<List<ScheduleMasterDTO>> getByName(@RequestParam(required=false) String keyword) {
        var scheduleDTOs = scheduleService.findByName(keyword);
        return ResponseEntity.ok(scheduleDTOs);
    }

    // Get all available schedules
    @GetMapping("/available-schedules")
    @Operation()
    @ApiResponse()
    public ResponseEntity<List<ScheduleMasterDTO>> getAllAvailableSchedules(@Valid @RequestBody ShowDateDTO showDateDTO, @RequestBody RoomDTO cinemaRoomDTO, @PathVariable UUID movieId) {
        var scheduleDTOs = scheduleService.findAllScheduleAvailable(showDateDTO, cinemaRoomDTO, movieId);
        return ResponseEntity.ok(scheduleDTOs);
    }

    // get by keyword and pagination
    @GetMapping("/search-paginated")
    @Operation(summary = "Search schedules with pagination")
    @ApiResponse(responseCode = "200", description = "Return schedules that match the keyword with pagination")
    public ResponseEntity<?> getPagination(@RequestParam(required=false) String keyword, @RequestParam(required=false, defaultValue="name") String sortBy, @RequestParam(required=false, defaultValue="asc") String order, @RequestParam(required=false, defaultValue="0") int page, @RequestParam(required=false, defaultValue="10") int size) {
        Pageable pageable = null;

        if (order.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        var scheduleDTOs = scheduleService.findPagination(keyword, pageable);

        var pageModel = pagedResourcesAssembler.toModel(scheduleDTOs);

        Collection<EntityModel<ScheduleMasterDTO>> data = pageModel.getContent();

        var links = pageModel.getLinks();

        var response = new CustomPagedResponse<EntityModel<ScheduleMasterDTO>>(data, pageModel.getMetadata(), links);

        return ResponseEntity.ok(response);
    }

    // get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get schedule by id")
    @ApiResponse(responseCode = "200", description = "Return schedule that match the id")
    public ResponseEntity<ScheduleMasterDTO> getById(@PathVariable UUID id) {
        var scheduleMasterDTO = scheduleService.findById(id);
        return ResponseEntity.ok(scheduleMasterDTO);
    }

    // create
    @PostMapping()
    @Operation(summary = "Create new moive")
    @ApiResponse(responseCode = "200", description = "Return new created schedule")
    @ApiResponse(responseCode = "400", description = "Return error message if create failed")
    public ResponseEntity<?> create(@Valid @RequestBody ScheduleCreateUpdateDTO scheduleCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var newSchedule = scheduleService.create(scheduleCreateUpdateDTO);

        if (newSchedule == null) {
            return ResponseEntity.badRequest().body("Fail to create new schedule");
        }

        return ResponseEntity.ok(newSchedule);
    }

    // edit
    @PutMapping("/{id}")
    @Operation(summary = "Update schedule by id")
    @ApiResponse(responseCode = "200", description = "Return updated schedule")
    @ApiResponse(responseCode = "400", description = "Return error message if updated failed")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody ScheduleCreateUpdateDTO scheduleCreateUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        var updatedSchedule = scheduleService.update(id, scheduleCreateUpdateDTO);

        if (updatedSchedule == null) {
            return ResponseEntity.badRequest().body("Failed to update schedule");
        }

        return ResponseEntity.ok(updatedSchedule);
    }

    // delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove schedule by id")
    @ApiResponse(responseCode = "200", description = "Return true id delete successfully")
    @ApiResponse(responseCode = "400", description = "Return error message if delete failed")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var result = scheduleService.delete(id);

        if (!result) {
            return ResponseEntity.badRequest().body("Failed to delete schedule");
        }

        return ResponseEntity.ok(result);
    }
}
