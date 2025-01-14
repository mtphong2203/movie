package com.jaf.movietheater.dtos.seats;

import java.util.UUID;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;
import com.jaf.movietheater.entities.SeatStatus;
import com.jaf.movietheater.enums.SeatType;

import lombok.*;
import jakarta.validation.constraints.NotNull;
@Getter
@Setter
public class SeatCreateUpdateDTO extends MasterCreateUpdateDTO {
    @NotNull
    private String seat_column;
    @NotNull
    private String seat_row;
    @NotNull
    private SeatType type;
    @NotNull
    private SeatStatus status;
    @NotNull
    private UUID roomId;
}