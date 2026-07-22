package com.example.Sharian.service;

import com.example.Sharian.dto.RegisterRequestDTO;
import com.example.Sharian.dto.RegisterResponseDTO;
import com.example.Sharian.entity.Role;
import com.example.Sharian.entity.User;
import com.example.Sharian.entity.UserRole;
import com.example.Sharian.repository.RoleRepository;
import com.example.Sharian.repository.UserRepository;
import com.example.Sharian.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO register(RegisterRequestDTO requestDTO){

        if(userRepository.existsByUsername(requestDTO.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(requestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setEmail(requestDTO.getEmail());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setPhoneNumber(requestDTO.getPhoneNumber());

        userRepository.save(user);

        Role defaultRole = roleRepository.findByCode("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));


        UserRole userRole = new UserRole();

        userRole.setUser(user);
        userRole.setRole(defaultRole);

        user.getUserRoles().add(userRole);

        userRoleRepository.save(userRole);

        return convertToRegisterResponse(user);


    }

    private RegisterResponseDTO convertToRegisterResponse(User user){
        List<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .toList();

        return new RegisterResponseDTO(
                user.getId(),
                user.getUsername(),
                roles
        );
    }
}
