package com.jaf.movietheater.dtos.schedule;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateUpdateDTO extends MasterCreateUpdateDTO{

    @NotBlank(message = "ScheduleTime cannot be blank")
    private String scheduleTime;
}
