package com.jaf.movietheater.dtos.roles;

import com.jaf.movietheater.dtos.MasterDTO;

public class RoleMasterDTO extends MasterDTO {
    private String name;

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
