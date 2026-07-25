package com.example.Sharyan.service;

import com.example.Sharyan.dto.LoginRequestDTO;
import com.example.Sharyan.dto.LoginResponseDTO;
import com.example.Sharyan.entity.User;
import com.example.Sharyan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO requestDTO){

        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(requestDTO.getPassword() ,user.getPassword())){

            throw new RuntimeException("Password incorrect");
        }


        String token = jwtService.generateToken(user);

        List<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .toList();

        return new LoginResponseDTO(
                token,
                user.getId(),
                user.getUsername(),
                roles,
                "Login successful"
        );
    }
}
