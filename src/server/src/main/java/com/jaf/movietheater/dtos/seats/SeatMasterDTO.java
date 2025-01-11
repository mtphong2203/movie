package com.jaf.movietheater.dtos.seats;

import java.util.UUID;

import com.jaf.movietheater.dtos.MasterDTO;
import com.jaf.movietheater.entities.SeatStatus;
import com.jaf.movietheater.entities.SeatType;
import lombok.*;

@Getter
@Setter
public class SeatMasterDTO extends MasterDTO {
    private UUID id;
    private String seat_column;
    private String seat_row;
    private SeatType type;
    private SeatStatus status;
    private UUID roomId;
}