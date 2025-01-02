package com.jaf.movietheater.mappers;

import org.mapstruct.*;

import com.jaf.movietheater.dtos.auth.RegisterRequestDTO;
import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.entities.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateUpdateDTO DTO);

    User toEntity(RegisterRequestDTO registerDTO);

    User toEntity(UserCreateUpdateDTO DTO, @MappingTarget User user);

    UserDTO toDTO(User user);

    UserMasterDTO toMasterDTO(User user);
}
