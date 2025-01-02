package com.jaf.movietheater.dtos.movie;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieCreateUpdateDTO extends MasterCreateUpdateDTO{

    @NotBlank(message="Movie Name VN cannot be blank")
    @Length(min=2, max=255, message="Movie name must be between 2 and 255 characters")
    private String name;

    @NotBlank(message="Version cannot be blank")
    @Length(min=1, max=20, message="Version must be between 1 and 20 characters")
    private String version;

    @NotBlank(message="Actor cannot be blank")
    @Length(min=2, max=50, message="Actor must be between 2 and 50 characters")
    private String actor;

    @NotBlank(message="Director cannot be blank")
    @Length(min=2, max=50, message="Director must be between 2 and 50 characters")
    private String director;

    @NotBlank(message="Actor cannot be content")
    @Length(min=10, max=1000, message="Content must be between 50 and 1000 characters")
    private String content;
    
    @NotNull(message="duration is required")
    private int duration;

    @NotNull(message="fromdate is required")
    private LocalDate fromDate;

    @NotNull(message="ToDate is required")
    private LocalDate toDate;

    @NotBlank(message="Company cannot be blank")
    @Length(min=2, max=100, message="Company must be between 2 and 100 characters")
    private String movieCompany;

    @NotBlank(message="Small Image cannot be blank")
    @Length(min=2, max=255, message="Small Image must be between 2 and 255 characters")
    private String thumbnailUrl;

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

    public MovieCreateUpdateDTO(
            @NotBlank(message = "Movie Name VN cannot be blank") @Length(min = 2, max = 255, message = "Movie name must be between 2 and 255 characters") String name,
            @NotBlank(message = "Version cannot be blank") @Length(min = 1, max = 20, message = "Version must be between 1 and 20 characters") String version,
            @NotBlank(message = "Actor cannot be blank") @Length(min = 2, max = 50, message = "Actor must be between 2 and 50 characters") String actor,
            @NotBlank(message = "Director cannot be blank") @Length(min = 2, max = 50, message = "Director must be between 2 and 50 characters") String director,
            @NotBlank(message = "Actor cannot be content") @Length(min = 50, max = 1000, message = "Content must be between 50 and 1000 characters") String content,
            @NotNull(message = "duration is required") int duration,
            @NotNull(message = "fromdate is required") LocalDate fromDate,
            @NotNull(message = "ToDate is required") LocalDate toDate,
            @NotBlank(message = "Company cannot be blank") @Length(min = 2, max = 100, message = "Company must be between 2 and 100 characters") String movieCompany,
            @NotBlank(message = "Small Image cannot be blank") @Length(min = 2, max = 255, message = "Small Image must be between 2 and 255 characters") String thumbnailUrl) {
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

    public MovieCreateUpdateDTO() {
    }
}
