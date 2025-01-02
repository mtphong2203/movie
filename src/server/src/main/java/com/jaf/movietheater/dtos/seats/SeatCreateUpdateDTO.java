package com.jaf.movietheater.dtos.seats;

import com.jaf.movietheater.entities.SeatType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
@Data
public class SeatCreateUpdateDTO {
    @NotNull
    private String seatColumn;
    @NotNull
    private int seatRow;
    @NotNull
    private SeatType type;
    @NotNull
    private boolean status;
    @NotNull
    private Long roomId;
}