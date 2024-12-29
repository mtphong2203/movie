package com.jaf.movietheater.dtos.promotion;

import java.time.LocalDateTime;

import com.jaf.movietheater.dtos.MasterDTO;

import jakarta.persistence.Column;

public class PromotionMasterDTO extends MasterDTO {
    private String title;

    private String description;

    private double discountLevel;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
    
    private String imgUrl;
}
