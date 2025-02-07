package com.jaf.movietheater.dtos.user;

import java.time.LocalDate;
import java.util.List;

import com.jaf.movietheater.dtos.BaseDTO;
import com.jaf.movietheater.enums.Gender;

import lombok.*;

@Getter
@Setter
public class UserDTO extends BaseDTO {

    private String firstName;

    private String lastName;

    private String displayName;

    private String username;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String email;

    private String thumbnailUrl;

    private String phoneNumber;

    private String address;

    private List<String> role;

}
