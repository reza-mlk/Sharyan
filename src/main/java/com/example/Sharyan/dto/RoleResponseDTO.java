package com.example.Sharyan.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponseDTO {

    private UUID id;

    private String name;

    private String code;

    private String description;

    private boolean enabled;
}
