package com.jaf.movietheater.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Table(name = "movie_types")
public class MovieType extends MasterEntity{

    @Column(name = "type_name", nullable = false, unique = true, columnDefinition = "NVARCHAR(255)")
    private String typeName;
    
    //Many To Many
    // MovieTypes - Movies
    @ManyToMany(mappedBy = "movieTypes")
    private Set<Movie> movies;
}
