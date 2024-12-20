package com.jaf.movietheater.services;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateAccessToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);
}
