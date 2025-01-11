package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.jaf.movietheater.dtos.room.*;
import com.jaf.movietheater.entities.Room;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface RoomMapper {
    Room toEntity(RoomCreateUpdateDTO DTO);

    Room toEntity(RoomCreateUpdateDTO DTO,@MappingTarget Room room);

    @Mapping(target = "cinemaId", source = "cinema.id")
    RoomMasterDTO toDTO(Room room);
}
