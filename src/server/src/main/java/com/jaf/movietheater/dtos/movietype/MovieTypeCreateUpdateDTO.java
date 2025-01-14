package com.jaf.movietheater.dtos.movietype;

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
public class MovieTypeCreateUpdateDTO extends MasterCreateUpdateDTO{

    @NotBlank(message = "NameType cannot be blank")
    private String typeName;
}
