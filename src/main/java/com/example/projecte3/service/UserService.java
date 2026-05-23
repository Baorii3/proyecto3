package com.example.projecte3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.projecte3.dto.UserResponseDTO;
import com.example.projecte3.mapper.UserMapper;
import com.example.projecte3.model.Role;
import com.example.projecte3.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserResponseDTO findById(String id) {
        return userRepository.findById(id).map(userMapper::toDto).orElse(null);
    }

    public List<UserResponseDTO> findByRole(Role role) {
        return userRepository.findByRole(role).stream().map(userMapper::toDto).toList();
    }

    public UserResponseDTO findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDto).orElse(null);
    }
}
