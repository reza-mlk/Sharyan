package com.example.Sharyan.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class RoleMenuResponseDTO {

    private boolean status;

    private String message;

    private UUID roleId;

    private  String roleName;

    private UUID menuId;

    private String menuTitle;

    private Set<String> roleMenus;

    private Set<String> menuRoles;
}
