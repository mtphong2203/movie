package com.jaf.movietheater.services;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaf.movietheater.dtos.user.UserCreateUpdateDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.dtos.user.UserUpdateDTO;
import com.jaf.movietheater.entities.Role;
import com.jaf.movietheater.entities.User;
import com.jaf.movietheater.enums.Gender;
import com.jaf.movietheater.exceptions.ResourceNotFoundException;
import com.jaf.movietheater.mappers.UserMapper;
import com.jaf.movietheater.repository.RoleRepository;
import com.jaf.movietheater.repository.UserRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        var users = userRepository.findAll();

        var userMasters = users.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            // Set role to user master
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
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
        // Set role to user master
        userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        return userMaster;
    }

    @Override
    public List<UserMasterDTO> searchByName(String keyword) {
        Specification<User> spec = (root, query, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));
        };

        List<User> users = userRepository.findAll(spec);

        List<UserMasterDTO> userMasters = users.stream().map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            // Set role to user master
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public Page<UserMasterDTO> searchPaginated(String keyword, String phoneNumber, List<Gender> gender,
            Pageable pageable) {
        Specification<User> spec = (root, query, cb) -> {
            Predicate proPredicate = cb.conjunction();

            if (keyword != null && !keyword.isEmpty()) {
                Predicate namePredicate = cb.or(
                        cb.like(cb.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%"));

                proPredicate = cb.and(proPredicate, namePredicate);
            }

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Predicate phonePredicate = cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%");

                proPredicate = cb.and(proPredicate, phonePredicate);
            }

            if (gender != null) {
                Predicate genderPredicate = root.get("gender").in(gender);

                proPredicate = cb.and(proPredicate, genderPredicate);
            }

            return proPredicate;

        };

        Page<User> users = userRepository.findAll(spec, pageable);

        Page<UserMasterDTO> userMasters = users.map(user -> {
            var userMaster = userMapper.toMasterDTO(user);
            // Set role to user master
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
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
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Check if user has role
        if (userDTO.getRoleId() != null) {
            // Get role
            var role = roleRepository.findById(userDTO.getRoleId());
            if (role != null) {
                // Set to user
                newUser.setRoles(Collections.singleton(role.get()));
            }
        }

        if (userDTO.getRoleName() != null) {

            List<Role> roles = roleRepository.findByNameIn(userDTO.getRoleName());

            if (roles != null) {
                // Set to user
                newUser.setRoles(new HashSet<>(roles));
            }
        }

        // Check password match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        newUser = userRepository.save(newUser);

        UserMasterDTO userMaster = userMapper.toMasterDTO(newUser);
        // Set role to user master
        if (newUser.getRoles() != null) {
            userMaster.setRole(newUser.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

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

        // Check password match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password is not match");
        }

        user = userMapper.toEntity(userDTO, user);
        user.setUpdatedAt(ZonedDateTime.now());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Check if user has role
        if (userDTO.getRoleId() != null) {
            // Get role
            var role = roleRepository.findById(userDTO.getRoleId());
            if (role.isPresent()) {
                // Set to user
                user.setRoles(Collections.singleton(role.get()));
            }
        }

        if (userDTO.getRoleName() != null) {

            List<Role> roles = roleRepository.findByNameIn(userDTO.getRoleName());

            if (roles != null) {
                // Set to user
                user.setRoles(new HashSet<>(roles));
            }
        }

        user = userRepository.save(user);

        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        // Set role to user master
        if (user.getRoles() != null) {
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

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

    @Override
    public UserMasterDTO update(UUID id, UserUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User create is required");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        user = userMapper.toEntity(userDTO, user);
        user.setUpdatedAt(ZonedDateTime.now());

        // Check if user has role
        if (userDTO.getRoleId() != null) {
            // Get role
            var role = roleRepository.findById(userDTO.getRoleId());
            if (role.isPresent()) {
                // Set to user
                user.setRoles(Collections.singleton(role.get()));
            }
        }

        if (userDTO.getRoleName() != null) {

            List<Role> roles = roleRepository.findByNameIn(userDTO.getRoleName());

            if (roles != null) {
                // Set to user
                user.setRoles(new HashSet<>(roles));
            }
        }
        user = userRepository.save(user);

        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        // Set role to user master
        if (user.getRoles() != null) {
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }

        return userMaster;
    }

}
