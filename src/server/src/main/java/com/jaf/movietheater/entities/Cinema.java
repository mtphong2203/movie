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

    @OneToMany(mappedBy = "cinema")
    private List<Room> rooms;

}
