package com.jaf.movietheater.dtos.promotion;

import java.time.LocalDateTime;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;

import jakarta.persistence.Column;

public class PromotionCreateUpdateDTO extends MasterCreateUpdateDTO {
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "discount_level", nullable = false)
    private double discountLevel;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
    
    @Column(name = "img_url", nullable = false)
    private String imgUrl;
}
