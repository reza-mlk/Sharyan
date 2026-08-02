package com.example.Sharyan.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.boot.model.internal.StrictIdGeneratorResolverSecondPass;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserResponseDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Set<String> roles;

    private Set<String> permissions;
}
