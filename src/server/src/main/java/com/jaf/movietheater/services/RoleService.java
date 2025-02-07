package com.jaf.movietheater.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaf.movietheater.dtos.roles.RoleCreateUpdateDTO;
import com.jaf.movietheater.dtos.roles.RoleMasterDTO;

public interface RoleService {
    List<RoleMasterDTO> getAll();

    RoleMasterDTO getById(UUID id);

    List<RoleMasterDTO> searchByName(String keyword);

    Page<RoleMasterDTO> searchPaginated(String keyword, Pageable pageable);

    RoleMasterDTO create(RoleCreateUpdateDTO roleDTO);

    RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO);

    boolean delete(UUID id);
}
