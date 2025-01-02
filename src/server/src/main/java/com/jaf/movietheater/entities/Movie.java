package com.jaf.movietheater.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="movies")
public class Movie extends MasterEntity{
    @Column(name="name", nullable=false, columnDefinition="VARCHAR(255)")
    private String name;

    @Column(name="version", nullable=false, columnDefinition="VARCHAR(20)")
    private String version;

    @Column(name="actor", nullable=false, columnDefinition="NVARCHAR(255)")
    private String actor;

    @Column(name="director", nullable=false, columnDefinition="NVARCHAR(255)")
    private String director;

    @Column(name="content", nullable=false, columnDefinition="NVARCHAR(1000)")
    private String content;

    @Column(name="duration", nullable=false, columnDefinition="INT")
    private int duration;

    @Column(name="from_date", nullable=false, columnDefinition="DATE")
    private LocalDate fromDate;

    @Column(name="to_date", nullable=false, columnDefinition="DATE")
    private LocalDate toDate;

    @Column(name="movie_product_company", nullable=false, columnDefinition="NVARCHAR(255)")
    private String movieCompany;

    @Column(name="large_image")
    private String thumbnailUrl;


    public Movie(String name, String version, String actor, String director, String content, int duration,
            LocalDate fromDate, LocalDate toDate, String movieCompany, String thumbnailUrl) {
        this.name = name;
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

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
