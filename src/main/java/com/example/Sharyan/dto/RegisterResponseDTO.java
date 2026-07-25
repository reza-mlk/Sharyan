package com.example.Sharyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegisterResponseDTO {

    private UUID id;

    private String username;

    private List<String> roles;
}
