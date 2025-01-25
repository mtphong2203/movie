package com.jaf.movietheater.dtos.user;

import java.time.LocalDate;
import java.util.Set;

import com.jaf.movietheater.dtos.MasterDTO;
import com.jaf.movietheater.enums.Gender;

import lombok.*;

@Getter
@Setter
public class UserMasterDTO extends MasterDTO {

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

    // show role on user
    private Set<String> role;

}
