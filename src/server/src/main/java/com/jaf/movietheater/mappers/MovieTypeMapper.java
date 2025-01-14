package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.movietype.MovieTypeCreateUpdateDTO;
import com.jaf.movietheater.dtos.movietype.MovieTypeDTO;
import com.jaf.movietheater.dtos.movietype.MovieTypeMasterDTO;
import com.jaf.movietheater.entities.MovieType;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface MovieTypeMapper {
    MovieTypeDTO toDTO(MovieType entity);

    MovieTypeMasterDTO toMasterDTO(MovieType entity);

    MovieType toEntity(MovieTypeCreateUpdateDTO dto);

    // Keep the insertedAt, updatedAt, deletedAt, isActive fields as they are
    @Mapping(target="createdAt", ignore=true)
    @Mapping(target="updatedAt", ignore=true)
    @Mapping(target="deletedAt", ignore=true)
    @Mapping(target="active", ignore=true)
    void updatedEntity(MovieTypeCreateUpdateDTO dto, @MappingTarget MovieType entity);
}
