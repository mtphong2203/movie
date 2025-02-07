package com.jaf.movietheater.dtos.showdate;

import java.time.LocalDate;

import com.jaf.movietheater.dtos.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowDateDTO extends BaseDTO{
    
    private String dateName;

    private LocalDate showDate;
}
