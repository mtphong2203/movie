package com.jaf.movietheater.entities;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MovieScheduleShowDateRoomId {

    private UUID roomId;

    private UUID showDateId;

    private UUID scheduleId;
}
