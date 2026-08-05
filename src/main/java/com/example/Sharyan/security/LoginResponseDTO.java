package com.example.Sharyan.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private UUID id;

    private String username;

    private Set<String> roles;

    private String message;
}
