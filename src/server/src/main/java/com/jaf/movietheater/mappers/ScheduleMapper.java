package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.schedule.ScheduleCreateUpdateDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleDTO;
import com.jaf.movietheater.dtos.schedule.ScheduleMasterDTO;
import com.jaf.movietheater.entities.Schedule;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ScheduleMapper {
    ScheduleDTO toDTO(Schedule entity);

    ScheduleMasterDTO toMasterDTO(Schedule entity);

    Schedule toEntity(ScheduleCreateUpdateDTO dto);

    // Keep the insertedAt, updatedAt, deletedAt, isActive fields as they are
    @Mapping(target="createdAt", ignore=true)
    @Mapping(target="updatedAt", ignore=true)
    @Mapping(target="deletedAt", ignore=true)
    @Mapping(target="active", ignore=true)
    void updatedEntity(ScheduleCreateUpdateDTO dto, @MappingTarget Schedule entity);
}
