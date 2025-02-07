package com.jaf.movietheater.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaf.movietheater.dtos.auth.RegisterRequestDTO;
import com.jaf.movietheater.dtos.user.UserDTO;
import com.jaf.movietheater.dtos.user.UserMasterDTO;
import com.jaf.movietheater.entities.Role;
import com.jaf.movietheater.entities.User;
import com.jaf.movietheater.mappers.UserMapper;
import com.jaf.movietheater.repository.RoleRepository;
import com.jaf.movietheater.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(auth -> "ROLE_" + auth.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);

    }

    @Override
    public UserMasterDTO register(RegisterRequestDTO registerDTO) {
        User user = userRepository.findByUsername(registerDTO.getUsername());

        if (user != null) {
            throw new IllegalArgumentException("User name is already exists");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }

        user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        user = userRepository.save(user);

        return userMapper.toMasterDTO(user);

    }

    @Override
    public String registerByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email is required");
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            throw new IllegalArgumentException("Email is already exist");
        }

        String newPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        Role userRole = roleRepository.findByName("CUSTOMER");
        if (userRole == null) {
            throw new IllegalArgumentException("Role 'CUSTOMER' does not exist");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(email);
        newUser.setPassword(passwordEncoder.encode(newPassword));
        newUser.setRoles(new HashSet<>(Set.of(userRole)));
        newUser = userRepository.save(newUser);

        emailService.sendAccountCreationEmail(email, email, newPassword);

        return newUser.getUsername();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDTO getUserInformation(String username) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
        return userDTO;
    }

}
