package com.jaf.movietheater.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.jaf.movietheater.dtos.promotion.PromotionCreateUpdateDTO;
import com.jaf.movietheater.dtos.promotion.PromotionMasterDTO;
import com.jaf.movietheater.entities.Promotion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionMapper {
    Promotion toEntity(PromotionCreateUpdateDTO DTO);

    Promotion toEntity(PromotionCreateUpdateDTO DTO, @MappingTarget Promotion promotion);

    PromotionMasterDTO toMasterDTO(Promotion promotion);
}
