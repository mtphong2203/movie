package com.jaf.movietheater.dtos.auth;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.jaf.movietheater.enums.Gender;

import jakarta.validation.constraints.NotNull;

public class RegisterRequestDTO {
    @Length(max = 50, message = "Maximum is 50 characters")
    private String firstName;

    @Length(max = 50, message = "Maximum is 50 characters")
    private String lastName;

    @NotNull(message = "User name is required")
    @Length(max = 40, message = "Maximum is 40 characters")
    private String username;

    // getter and setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private LocalDate dateOfBirth;

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
}
