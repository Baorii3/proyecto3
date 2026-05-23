package com.example.projecte3.mapper;

import com.example.projecte3.dto.AcademicProfileDTO;
import com.example.projecte3.dto.UserRequestDTO;
import com.example.projecte3.dto.UserResponseDTO;
import com.example.projecte3.model.AcademicProfile;
import com.example.projecte3.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponseDTO toDto(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setDataCreated(user.getDataCreated());

        if (user.getAcademicProfile() != null) {
            AcademicProfile profile = user.getAcademicProfile();
            AcademicProfileDTO profileDTO = new AcademicProfileDTO();
            profileDTO.setGrade(profile.getGrade());
            profileDTO.setCourse(profile.getCourse());
            profileDTO.setObservations(profile.getObservations());
            profileDTO.setStatus(profile.getStatus());
            dto.setAcademicProfile(profileDTO);
        }

        return dto;
    }

    public User toEntity(UserRequestDTO request) {
        if (request == null) return null;

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setDataCreated(LocalDateTime.now());

        if (request.getGrade() != null) {
            AcademicProfile profile = new AcademicProfile();
            profile.setGrade(request.getGrade());
            profile.setCourse(request.getCourse());
            profile.setObservations(request.getObservations());
            user.setAcademicProfile(profile);
        }

        return user;
    }
}
