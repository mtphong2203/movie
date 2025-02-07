package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaf.movietheater.dtos.promotion.PromotionCreateUpdateDTO;
import com.jaf.movietheater.dtos.promotion.PromotionMasterDTO;
import com.jaf.movietheater.entities.Promotion;
import com.jaf.movietheater.mappers.PromotionMapper;
import com.jaf.movietheater.repository.PromotionRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;
    
    @Override
    public List<PromotionMasterDTO> getAll() {
        var promotions = promotionRepository.findAll();

        var promotionMasterDTOs = promotions.stream().map(promotion -> {
            var promotionMasterDTO = promotionMapper.toMasterDTO(promotion);
            return promotionMasterDTO;
        }).toList();

        return promotionMasterDTOs;
    }

    @Override
    public Page<PromotionMasterDTO> getAll(String keyword, Pageable pageable) {
        Specification<Promotion> specification = (root, query, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            Predicate namePredicate = criteriaBuilder.like(root.get("title"), "%" + keyword + "%");

            return criteriaBuilder.or(namePredicate);
        };

        var promotions = promotionRepository.findAll(specification, pageable);

        var promotionMasterDTOs = promotions.map(promotion -> {
            var promotionMasterDTO = promotionMapper.toMasterDTO(promotion);
            return promotionMasterDTO;
        });

        return promotionMasterDTOs;
    }

    @Override
    public PromotionMasterDTO getById(UUID id) {
        var promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            return null;
        }

        var promotionMasterDTO = promotionMapper.toMasterDTO(promotion);

        return promotionMasterDTO;
    }

    @Override
    public PromotionMasterDTO create(PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        if (promotionCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Promotion is required");
        }

        var promotion = promotionMapper.toEntity(promotionCreateUpdateDTO);
        
        promotion = promotionRepository.save(promotion);

        var promotionMasterDTO = promotionMapper.toMasterDTO(promotion);

        return promotionMasterDTO;
    }

    @Override
    public PromotionMasterDTO update(UUID id, PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        if (promotionCreateUpdateDTO == null) {
            throw new IllegalArgumentException("Promotion is required");
        }

        var promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            return null;
        }

        promotionMapper.toEntity(promotionCreateUpdateDTO, promotion);

        promotion = promotionRepository.save(promotion);

        var promotionMasterDTO = promotionMapper.toMasterDTO(promotion);

        return promotionMasterDTO;
    }

    @Override
    public boolean deleteById(UUID id) {
        var promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            return false;
        }

        promotionRepository.delete(promotion);

        return true;
    }

}
