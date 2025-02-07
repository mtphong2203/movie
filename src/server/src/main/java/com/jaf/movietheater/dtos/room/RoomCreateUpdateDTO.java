package com.jaf.movietheater.dtos.room;

import java.util.UUID;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;
import com.jaf.movietheater.entities.RoomType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateUpdateDTO extends MasterCreateUpdateDTO {
    @NotNull(message = "Room name is required")
    private String name;

    @NotNull(message = "Capacity is required")
    private int capacity;

    @NotNull(message = "Room type is required")
    private RoomType type;

    @NotNull(message = "Cinema ID is required")
    private UUID cinemaId;
}