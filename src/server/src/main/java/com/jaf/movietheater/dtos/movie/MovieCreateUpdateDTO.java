package com.jaf.movietheater.dtos.movie;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Length(min=2, max=255, message="Small Image must be between 2 and 255 characters")
    private String thumbnailUrl;

    private Set<UUID> roomIds;

    private Set<UUID> scheduleIds;

    private Set<UUID> showDateIds;

    private Set<UUID> movieTypeIds;
}
