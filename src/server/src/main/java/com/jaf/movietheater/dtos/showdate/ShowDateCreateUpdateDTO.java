package com.jaf.movietheater.dtos.showdate;

import java.time.LocalDate;


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
public class ShowDateCreateUpdateDTO extends MasterCreateUpdateDTO{

    @NotBlank(message = "DateName cannot be blank")
    private String dateName;

    private LocalDate showDate;
}
