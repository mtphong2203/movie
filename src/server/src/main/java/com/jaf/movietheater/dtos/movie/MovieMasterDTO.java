package com.jaf.movietheater.dtos.movie;

import java.time.LocalDate;

import com.jaf.movietheater.dtos.MasterDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieMasterDTO extends MasterDTO{

    private String name;

    private String version;

    private String actor;

    private String director;

    private String content;

    private int duration;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String movieCompany;

    private String thumbnailUrl;

}
