package com.jaf.movietheater.dtos.schedule;

import com.jaf.movietheater.dtos.MasterDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleMasterDTO extends MasterDTO{
    
    private String scheduleTime;
}
