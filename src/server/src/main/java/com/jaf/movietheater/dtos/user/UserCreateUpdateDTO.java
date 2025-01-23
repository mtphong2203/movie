package com.jaf.movietheater.dtos.user;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.dtos.MasterCreateUpdateDTO;
import com.jaf.movietheater.enums.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UserCreateUpdateDTO extends MasterCreateUpdateDTO {
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

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Confirm password is required")
    private String confirmPassword;

    private UUID roleId;

    private List<String> roleName;

}
