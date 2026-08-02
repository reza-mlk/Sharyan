package com.example.Sharyan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Setter
@Getter
@Builder
public class MenuResponseDTO {

    private UUID id;

    private String title;

    private String name;

    private String route;

    private String icon;

    private Integer sortOrder;

    private UUID parentId;

    private boolean enabled;

    private List<MenuResponseDTO> children;

    private Set<String> roles;
}


