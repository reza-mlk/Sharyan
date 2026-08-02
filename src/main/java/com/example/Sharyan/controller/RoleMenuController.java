package com.example.Sharyan.controller;


import com.example.Sharyan.dto.RoleMenuResponseDTO;
import com.example.Sharyan.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/role-menus")
@RequiredArgsConstructor
public class RoleMenuController {

    private final RoleMenuService roleMenuService;

    @PostMapping("/{roleId}/assign/{menuId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RoleMenuResponseDTO> assignMenu(
            @PathVariable UUID  roleId,
            @PathVariable UUID menuId
    ){

        return ResponseEntity.ok(roleMenuService.assignMenuToRole(roleId , menuId));
    }

    @DeleteMapping("/{roleId}/remove/{menuId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Boolean> removeMenu(
            @PathVariable UUID roleId,
            @PathVariable UUID menuId
            ){
        return ResponseEntity.ok(roleMenuService.removeMenuFromRole(roleId , menuId));
    }
}
