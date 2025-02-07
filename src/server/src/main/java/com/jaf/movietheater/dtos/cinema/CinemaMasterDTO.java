package com.jaf.movietheater.dtos.cinema;

import com.jaf.movietheater.dtos.MasterDTO;

public class CinemaMasterDTO extends MasterDTO {
    private String name;

    private String location;

    private String logoUrl;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLogoUrl() {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}