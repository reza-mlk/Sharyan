package com.example.Sharyan.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class MenuRequestDTO {

    private String title;

    private String name;

    private String route;

    private String icon;

    private Integer sortOrder;

    private boolean enabled;

    private UUID parentId;

}
