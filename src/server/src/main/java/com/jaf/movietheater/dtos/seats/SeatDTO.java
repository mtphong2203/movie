package com.jaf.movietheater.dtos.seats;

import java.util.UUID;

import com.jaf.movietheater.entities.SeatType;
import lombok.Data;

@Data
public class SeatDTO {
    private UUID id;
    private String seatColumn;
    private int seatRow;
    private SeatType type;
    private boolean status;
    private Long roomId;
}