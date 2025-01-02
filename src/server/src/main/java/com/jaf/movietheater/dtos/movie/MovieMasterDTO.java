package com.jaf.movietheater.dtos.movie;

import java.time.LocalDate;

import com.jaf.movietheater.dtos.MasterDTO;

public class MovieMasterDTO extends MasterDTO{

    private String movieName;

    private String version;

    private String actor;

    private String director;

    private String content;

    private int duration;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String movieCompany;

    private String thumbnailUrl;

    // Getter and Setter
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getMovieCompany() {
        return movieCompany;
    }

    public void setMovieCompany(String movieCompany) {
        this.movieCompany = movieCompany;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    // Constructor
    public MovieMasterDTO() {
    }

    public MovieMasterDTO(String movieName, String version, String actor, String director, String content, int duration,
            LocalDate fromDate, LocalDate toDate, String movieCompany, String thumbnailUrl) {
        this.movieName = movieName;
        this.version = version;
        this.actor = actor;
        this.director = director;
        this.content = content;
        this.duration = duration;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.movieCompany = movieCompany;
        this.thumbnailUrl = thumbnailUrl;
    }
}
