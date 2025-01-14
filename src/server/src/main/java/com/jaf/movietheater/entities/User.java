package com.jaf.movietheater.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.jaf.movietheater.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends MasterEntity {

    @Column(columnDefinition = "NVARCHAR(25)")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(25)")
    private String lastName;

    @Transient
    private String displayName;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(40)")
    private String username;

    private LocalDate dateOfBirth;

    @Column(nullable = true)
    private Gender gender;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(50)")
    private String email;

    @Column(nullable = true, unique = true, columnDefinition = "NVARCHAR(25)")
    private String phoneNumber;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String identityCard;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String thumbnailUrl;

    @Column(columnDefinition = "NVARCHAR(70)")
    private String address;

    @Column(nullable = false)
    private String password;

    // Many to many
    @ManyToMany()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices;
}
