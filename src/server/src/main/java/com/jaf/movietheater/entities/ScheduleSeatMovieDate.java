package com.jaf.movietheater.entities;

import com.jaf.movietheater.enums.SeatBookingStatus;
import com.jaf.movietheater.enums.SeatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "schedule_seat_movie_dates")
public class ScheduleSeatMovieDate extends MasterEntity{

    @ManyToOne
    @MapsId("id")
    @JoinColumns({
        @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id"),
        @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
        @JoinColumn(name = "show_date_id", referencedColumnName = "show_date_id")
    })
    private MovieScheduleShowDateRoom movieScheduleShowDateRoom;

    // seat
    @OneToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @Column(name = "seat_column", nullable = false, columnDefinition = "VARCHAR(255)")
    private String seat_column;

    @Column(name = "seat_row", nullable = false)
    private String seat_row;

    @Column(name = "type", nullable = false, columnDefinition = "INT")
    private SeatType type;

    @Column(name = "seat_booking_status", nullable = false, columnDefinition = "INT")
    private SeatBookingStatus seatBookingStatus;
}
