package com.example.projecte3.service;

import com.example.projecte3.dto.UserRequestDTO;
import com.example.projecte3.dto.UserResponseDTO;
import com.example.projecte3.mapper.UserMapper;
import com.example.projecte3.model.AcademicProfile;
import com.example.projecte3.model.Role;
import com.example.projecte3.model.User;
import com.example.projecte3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public List<UserResponseDTO> findByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserResponseDTO create(UserRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return null;
        }

        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserResponseDTO update(String id, UserRequestDTO request) {
        Optional<User> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }

        User user = existing.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());


        if (request.getGrade() != null) {
            AcademicProfile profile = new AcademicProfile();
            profile.setGrade(request.getGrade());
            profile.setCourse(request.getCourse());
            profile.setObservations(request.getObservations());
            user.setAcademicProfile(profile);
        } else {
            user.setAcademicProfile(null);
        }

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public boolean delete(String id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
