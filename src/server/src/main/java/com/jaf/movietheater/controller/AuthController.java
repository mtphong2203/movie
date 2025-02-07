package com.jaf.movietheater.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jaf.movietheater.dtos.auth.LoginRequestDTO;
import com.jaf.movietheater.dtos.auth.LoginResponseDTO;
import com.jaf.movietheater.dtos.auth.RegisterRequestDTO;
import com.jaf.movietheater.dtos.user.UserDTO;
import com.jaf.movietheater.services.AuthService;
import com.jaf.movietheater.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth APIs", description = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenService tokenService;

    public AuthController(AuthService authService, AuthenticationManagerBuilder managerBuilder,
            TokenService tokenService) {
        this.authService = authService;
        this.managerBuilder = managerBuilder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username and password")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenService.generateAccessToken(authentication);

        UserDTO userDTO = authService.getUserInformation(loginRequestDTO.getUsername());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        loginResponseDTO.setAccessToken(accessToken);
        loginResponseDTO.setUserDTO(userDTO);
        loginResponseDTO.setExpireTime(tokenService.getExpireTime());

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    @Operation(summary = "Sign up an account")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        var result = authService.register(registerRequestDTO);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/registerByEmail")
    @Operation(summary = "Register by email")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<?> registerByEmail(@RequestParam String email) {
        var result = authService.registerByEmail(email);

        return ResponseEntity.ok(result);
    }

}
