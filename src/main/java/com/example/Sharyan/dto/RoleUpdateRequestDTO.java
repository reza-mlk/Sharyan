package com.example.Sharyan.dto;

import lombok.Data;

@Data
public class RoleUpdateRequestDTO {

    private String name;

    private String description;

    private Boolean enabled;
}
