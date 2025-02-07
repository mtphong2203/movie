package com.jaf.movietheater.dtos.cinema;

import com.jaf.movietheater.dtos.SearchDTO;

public class CinemaSearchDTO extends SearchDTO {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
