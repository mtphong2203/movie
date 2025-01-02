package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.jaf.movietheater.dtos.seats.*;
import com.jaf.movietheater.entities.Seat;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SeatMapper {
    Seat toEntity(SeatCreateUpdateDTO DTO);

    Seat toEntity(SeatCreateUpdateDTO DTO,@MappingTarget Seat seat);

    SeatDTO toDTO(Seat seat);
}
