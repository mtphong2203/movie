package com.jaf.movietheater.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    // One to many
    @OneToMany(mappedBy = "movie")
    private Set<MovieScheduleShowDateRoom> movieScheduleShowDateRooms;

    // Many to many
    @ManyToMany
    @JoinTable(name = "movie_movie_types", joinColumns = @JoinColumn(name="movie_id"), inverseJoinColumns = @JoinColumn(name="movie_type_id"))
    private Set<MovieType> movieTypes;
}
