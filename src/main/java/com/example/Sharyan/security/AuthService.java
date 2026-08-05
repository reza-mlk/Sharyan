package com.example.Sharyan.security;

import com.example.Sharyan.entity.RefreshToken;
import com.example.Sharyan.entity.User;
import com.example.Sharyan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDTO login(LoginRequestDTO requestDTO){

        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(requestDTO.getPassword() ,user.getPassword())){

            throw new RuntimeException("Password incorrect");
        }


        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(user , refreshToken);

        Set<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .collect(Collectors.toSet());

        return new LoginResponseDTO(
                accessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                roles,
                "Login successful"
        );
    }

    public LoginResponseDTO refreshToken(String refreshToken){

        RefreshToken storedToken = refreshTokenService.findByToken(refreshToken);


        if(!refreshTokenService.isValid(storedToken)){
            throw new RuntimeException("Refresh token expired or revoked");
        }

        User user  = storedToken.getUser();

        String newAccessToken = jwtService.generateToken(user);

        Set<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .collect(Collectors.toSet());

        return new LoginResponseDTO(
                newAccessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                roles,
                "Token refreshed"
        );


    }
}
