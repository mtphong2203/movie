package com.jaf.movietheater.services;

import java.util.UUID;

import com.jaf.movietheater.dtos.auth.RegisterRequestDTO;

public interface AuthService {

    boolean existsByUsername(String username);

    UUID register(RegisterRequestDTO registerDTO);
}
