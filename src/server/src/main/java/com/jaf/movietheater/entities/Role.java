package com.jaf.movietheater.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends MasterEntity {
    @Column(unique = true, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(500)")
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
