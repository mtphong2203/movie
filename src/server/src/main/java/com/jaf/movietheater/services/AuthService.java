package com.jaf.movietheater.services;

import com.jaf.movietheater.dtos.auth.RegisterRequestDTO;
import com.jaf.movietheater.dtos.user.UserDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;

public interface AuthService {

    boolean existsByUsername(String username);

    UserMasterDTO register(RegisterRequestDTO registerDTO);

    UserDTO getUserInformation(String username);

    String registerByEmail(String email);
}
