package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.showdate.ShowDateCreateUpdateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateDTO;
import com.jaf.movietheater.dtos.showdate.ShowDateMasterDTO;
import com.jaf.movietheater.entities.ShowDate;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ShowDateMapper {
    ShowDateDTO toDTO(ShowDate entity);

    ShowDateMasterDTO toMasterDTO(ShowDate entity);

    ShowDate toEntity(ShowDateCreateUpdateDTO dto);

    ShowDate toEntity(ShowDateDTO dto);

    // Keep the insertedAt, updatedAt, deletedAt, isActive fields as they are
    @Mapping(target="createdAt", ignore=true)
    @Mapping(target="updatedAt", ignore=true)
    @Mapping(target="deletedAt", ignore=true)
    @Mapping(target="active", ignore=true)
    void updatedEntity(ShowDateCreateUpdateDTO dto, @MappingTarget ShowDate entity);
}
