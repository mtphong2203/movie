package com.jaf.movietheater.dtos.room;

import java.util.UUID;

import com.jaf.movietheater.dtos.MasterDTO;
import com.jaf.movietheater.entities.RoomType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomMasterDTO extends MasterDTO {
    private String name;
    private int capacity;
    private RoomType type;
    private UUID cinemaId;
}