package com.jaf.movietheater.dtos.auth;

import com.jaf.movietheater.dtos.user.UserDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {

    private String accessToken;

    private Integer expireTime;

    private UserDTO userDTO;

}
