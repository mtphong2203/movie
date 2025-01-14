package com.jaf.movietheater.entities;

import java.util.Set;

import com.jaf.movietheater.enums.SeatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seats")
public class Seat extends MasterEntity{
    @Column(name = "seat_column", nullable = false, length = 10)
    private String seat_column;

    @Column(name = "seat_row", nullable = false)
    private String seat_row;

    @Column(name = "type", nullable = false)
    private SeatType type;

    @Column(name = "status", nullable = false)
    private SeatStatus status;

    //One To One
    @OneToOne(mappedBy = "seat")
    private ScheduleSeatMovieDate scheduleSeatMovieDate;

    //Many To One
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

}
