package com.jaf.movietheater.dtos.user;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;
import com.jaf.movietheater.entities.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UserUpdateDTO extends MasterCreateUpdateDTO {
    @Length(max = 50, message = "Maximum is 50 characters")
    private String firstName;

    @Length(max = 50, message = "Maximum is 50 characters")
    private String lastName;

    @NotNull(message = "User name is required")
    @Length(max = 40, message = "Maximum is 40 characters")
    private String username;

    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Email is required")
    @Length(max = 50, message = "Maximum is 50 characters")
    private String email;

    @NotNull(message = "Phone number is required")
    @Length(max = 25, message = "Maximum is 25 characters")
    private String phoneNumber;

    @Length(max = 70, message = "Maximum is 70 characters")
    private String address;

    private UUID roleId;

    private String roleName;

}
