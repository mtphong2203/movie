package com.jaf.movietheater.dtos.promotion;

import com.jaf.movietheater.dtos.SearchDTO;

public class PromotionSearchDTO extends SearchDTO {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
