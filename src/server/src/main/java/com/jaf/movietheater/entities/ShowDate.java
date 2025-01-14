package com.jaf.movietheater.entities;

import java.time.LocalDate;
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
@Table(name = "show_dates")
public class ShowDate extends MasterEntity{

    @Column(name = "date_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String dateName;

    @Column(name = "show_date", nullable = false, unique = true, columnDefinition = "DATE")
    private LocalDate showDate;

    // one to many
    // room
    @OneToMany(mappedBy = "showDate")
    private Set<MovieScheduleShowDateRoom> movieScheduleShowDateRooms;
}
