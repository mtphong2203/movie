package com.jaf.movietheater.dtos.roles;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;

import jakarta.validation.constraints.NotNull;

public class RoleCreateUpdateDTO extends MasterCreateUpdateDTO {

    @NotNull(message = "Name is required")
    private String name;

    @Length(max = 500, message = "Maximum is 500 characters")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
