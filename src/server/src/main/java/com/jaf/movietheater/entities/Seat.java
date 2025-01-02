package com.jaf.movietheater.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private int seat_row;

    @Column(name = "type", nullable = false)
    private SeatType type;

    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
