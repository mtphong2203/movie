package com.jaf.movietheater.dtos.movietype;

import com.jaf.movietheater.dtos.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieTypeDTO extends BaseDTO{

    private String typeName;
}
