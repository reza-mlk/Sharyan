package com.example.Sharian.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;

    private UUID id;

    private String username;

    private List<String> roles;

    private String message;
}
