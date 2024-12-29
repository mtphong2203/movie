package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.promotion.PromotionCreateUpdateDTO;
import com.jaf.movietheater.dtos.promotion.PromotionMasterDTO;

public interface PromotionService {
    List<PromotionMasterDTO> getAll();

    Page<PromotionMasterDTO> getAll(String keyword, Pageable pageable);

    PromotionMasterDTO getById(UUID id);

    PromotionMasterDTO create(PromotionCreateUpdateDTO promotionCreateUpdateDTO);

    PromotionMasterDTO update(UUID id, PromotionCreateUpdateDTO promotionCreateUpdateDTO);

    boolean deleteById(UUID id);
}
