package com.jaf.movietheater.dtos.room;

import com.jaf.movietheater.dtos.SearchDTO;

public class RoomSearchDTO extends SearchDTO {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }   

}
