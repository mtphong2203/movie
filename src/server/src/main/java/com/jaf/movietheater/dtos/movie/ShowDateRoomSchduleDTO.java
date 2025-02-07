package com.jaf.movietheater.dtos.movie;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowDateRoomSchduleDTO {
    
    private UUID showDateId;

    private UUID roomId;

    private UUID scheduleId;
}
