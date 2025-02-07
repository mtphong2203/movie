package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.dtos.user.UserUpdateDTO;
import com.jaf.movietheater.enums.Gender;

public interface UserService {

    List<UserMasterDTO> getAll();

    UserMasterDTO getById(UUID id);

    List<UserMasterDTO> searchByName(String keyword);

    Page<UserMasterDTO> searchPaginated(String keyword, String phoneNumber, List<Gender> gender, Pageable pageable);

    UserMasterDTO create(UserCreateUpdateDTO userDTO);

    UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO);

    UserMasterDTO update(UUID id, UserUpdateDTO userDTO);

    boolean delete(UUID id);
}
