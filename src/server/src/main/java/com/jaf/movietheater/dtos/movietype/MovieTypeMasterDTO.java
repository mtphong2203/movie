package com.jaf.movietheater.dtos.movietype;

import com.jaf.movietheater.dtos.MasterDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieTypeMasterDTO extends MasterDTO{

    private String typeName;
}
