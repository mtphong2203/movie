package com.jaf.movietheater.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.entities.User;
import com.jaf.movietheater.exceptions.ResourceNotFoundException;
import com.jaf.movietheater.mappers.UserMapper;
import com.jaf.movietheater.repository.RoleRepository;
import com.jaf.movietheater.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        var users = userRepository.findAll();

        var userMasters = users.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public UserMasterDTO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not found");
        }
        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        return userMaster;
    }

    @Override
    public List<UserMasterDTO> searchByName(String keyword) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));
        };

        List<User> users = userRepository.findAll(spec);

        List<UserMasterDTO> userMasters = users.stream().map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public Page<UserMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));
        };

        Page<User> users = userRepository.findAll(spec, pageable);

        Page<UserMasterDTO> userMasters = users.map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            return userMaster;
        });

        return userMasters;
    }

    @Override
    public UserMasterDTO create(UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create is required");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("User name is already exist!");
        }

        User newUser = userMapper.toEntity(userDTO);

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        newUser = userRepository.save(newUser);

        UserMasterDTO userMaster = userMapper.toMasterDTO(newUser);

        return userMaster;
    }

    @Override
    public UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create is required");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        user = userMapper.toEntity(userDTO, user);
        user.setUpdatedAt(ZonedDateTime.now());

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        user = userRepository.save(user);

        UserMasterDTO userMaster = userMapper.toMasterDTO(user);

        return userMaster;
    }

    @Override
    public boolean delete(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        userRepository.delete(user);

        return !userRepository.existsById(id);
    }

}
