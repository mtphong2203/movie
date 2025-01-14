package com.jaf.movietheater.dtos.movie;

import java.time.LocalDate;

import com.jaf.movietheater.dtos.BaseDTO;

public class MovieDTO extends BaseDTO{

    private String name;

    private LocalDate fromDate;

    private String movieCompany;

    private int duration;
 
    private String version;

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getMovieCompany() {
        return movieCompany;
    }

    public void setMovieCompany(String movieCompany) {
        this.movieCompany = movieCompany;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    // Constructor
    public MovieDTO() {
    }

    public MovieDTO(String name, LocalDate fromDate, String movieCompany, int duration, String version) {
        this.name = name;
        this.fromDate = fromDate;
        this.movieCompany = movieCompany;
        this.duration = duration;
        this.version = version;
    }
}
