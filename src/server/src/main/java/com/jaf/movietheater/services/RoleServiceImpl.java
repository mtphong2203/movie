package com.jaf.movietheater.services;

import java.util.List;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jaf.movietheater.dtos.roles.RoleCreateUpdateDTO;
import com.jaf.movietheater.dtos.roles.RoleMasterDTO;
import com.jaf.movietheater.entities.Role;
import com.jaf.movietheater.exceptions.ResourceNotFoundException;
import com.jaf.movietheater.mappers.RoleMapper;
import com.jaf.movietheater.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleMasterDTO> getAll() {

        var roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toMasterDTO).toList();
    }

    @Override
    public RoleMasterDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not found");
        }
        return roleMapper.toMasterDTO(role);
    }

    @Override
    public List<RoleMasterDTO> searchByName(String keyword) {
        Specification<Role> spec = (root, query, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };

        List<Role> roles = roleRepository.findAll(spec);

        return roles.stream().map(roleMapper::toMasterDTO).toList();
    }

    @Override
    public Page<RoleMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<Role> spec = (root, query, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };

        Page<Role> roles = roleRepository.findAll(spec, pageable);

        return roles.map(roleMapper::toMasterDTO);
    }

    @Override
    public RoleMasterDTO create(RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create is required");
        }

        Role role = roleRepository.findByName(roleDTO.getName());

        if (role != null) {
            throw new IllegalArgumentException("Role name is already exist!");
        }

        Role newRole = roleMapper.toEntity(roleDTO);

        newRole = roleRepository.save(newRole);

        return roleMapper.toMasterDTO(newRole);
    }

    @Override
    public RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role create is required");
        }

        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        role = roleMapper.toEntity(roleDTO, role);
        role.setUpdatedAt(ZonedDateTime.now());

        role = roleRepository.save(role);

        return roleMapper.toMasterDTO(role);
    }

    @Override
    public boolean delete(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        roleRepository.delete(role);

        return !roleRepository.existsById(id);
    }

}
