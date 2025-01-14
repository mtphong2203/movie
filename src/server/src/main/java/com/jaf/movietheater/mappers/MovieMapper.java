package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.movie.MovieCreateUpdateDTO;
import com.jaf.movietheater.dtos.movie.MovieDTO;
import com.jaf.movietheater.dtos.movie.MovieMasterDTO;
import com.jaf.movietheater.entities.Movie;

@Mapper(componentModel= MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface MovieMapper {
    MovieDTO toDTO(Movie entity);

    MovieMasterDTO toMasterDTO(Movie entity);

    Movie toEntity(MovieCreateUpdateDTO dto);

    // Keep the insertedAt, updatedAt, deletedAt, isActive fields as they are
    @Mapping(target="createdAt", ignore=true)
    @Mapping(target="updatedAt", ignore=true)
    @Mapping(target="deletedAt", ignore=true)
    @Mapping(target="active", ignore=true)
    void updatedEntity(MovieCreateUpdateDTO dto, @MappingTarget Movie entity);
}
