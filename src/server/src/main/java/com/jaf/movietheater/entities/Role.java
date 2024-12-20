package com.jaf.movietheater.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends MasterEntity {
    @Column(unique = true, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // getter and setter
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

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
