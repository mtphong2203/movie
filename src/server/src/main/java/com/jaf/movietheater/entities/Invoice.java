package com.jaf.movietheater.entities;

import java.time.LocalDate;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice extends MasterEntity{

    @Column(name="movie_name", nullable=false, unique=true, columnDefinition="NVARCHAR(255)")
    private String movieName;

    @Column(name="booking_date", nullable=false)
    private LocalDate bookingDate;

    @Column(name="schedule_date", nullable=false)
    private LocalDate scheduleShow;

    @Column(name="schedule_show_time", nullable=false, columnDefinition="VARCHAR(255)")
    private String scheduleShowTime;

    @Column(name="seat", nullable=false, columnDefinition="VARCHAR(255)")
    private String seat;

    @Column(name="add_core", nullable=false)
    private double addScore;

    @Column(name="use_core", nullable=false)
    private double useScore;

    @Column(name="total_money", nullable=false)
    private double totalMoney;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
