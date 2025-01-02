package com.jaf.movietheater.dtos.user;

import java.time.LocalDate;
import java.util.List;

import com.jaf.movietheater.entities.Gender;

import lombok.*;

@Getter
@Setter
public class UserDTO {

    private String firstName;

    private String lastName;

    private String displayName;

    private String username;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String email;

    private String phoneNumber;

    private String address;

    private List<String> role;

}
