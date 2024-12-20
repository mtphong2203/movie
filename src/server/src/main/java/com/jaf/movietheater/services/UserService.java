package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;

public interface UserService {
    List<UserMasterDTO> getAll();

    UserMasterDTO getById(UUID id);

    List<UserMasterDTO> searchByName(String keyword);

    Page<UserMasterDTO> searchPaginated(String keyword, Pageable pageable);

    UserMasterDTO create(UserCreateUpdateDTO userDTO);

    UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO);

    boolean delete(UUID id);
}
