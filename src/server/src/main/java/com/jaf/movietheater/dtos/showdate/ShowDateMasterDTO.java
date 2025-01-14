package com.jaf.movietheater.dtos.showdate;

import java.time.LocalDate;

import com.jaf.movietheater.dtos.MasterDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowDateMasterDTO extends MasterDTO{

    private String dateName;

    private LocalDate showDate;
}
