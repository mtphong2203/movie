package com.jaf.movietheater.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cinemas")
public class Cinema extends MasterEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "logo_url", nullable = true)
    private String logoUrl;

    // Getters and Setters
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
