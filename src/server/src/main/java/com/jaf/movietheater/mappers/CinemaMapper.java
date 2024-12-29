package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.cinema.CinemaCreateUpdateDTO;
import com.jaf.movietheater.dtos.cinema.CinemaMasterDTO;
import com.jaf.movietheater.entities.Cinema;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CinemaMapper {
    Cinema toEntity(CinemaCreateUpdateDTO DTO);

    Cinema toEntity(CinemaCreateUpdateDTO DTO, @MappingTarget Cinema cinema);

    CinemaMasterDTO toMasterDTO(Cinema cinema);
}
