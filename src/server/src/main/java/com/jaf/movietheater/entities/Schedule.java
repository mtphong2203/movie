package com.jaf.movietheater.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "schedules")
public class Schedule extends MasterEntity{

    @Column(name = "schedule_time", nullable = false, unique = true)
    private String scheduleTime;

    @Column(name = "schedule_time_number")
    private Integer scheduleTimeNumber;

    //Onetomany
    // room
    @OneToMany(mappedBy = "schedule")
    private Set<MovieScheduleShowDateRoom> movieScheduleShowDateRooms;
}
