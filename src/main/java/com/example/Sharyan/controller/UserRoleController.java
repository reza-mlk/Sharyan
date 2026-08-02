package com.example.Sharyan.controller;

import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.repository.UserRoleRepository;
import com.example.Sharyan.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/{userId}/assign/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDTO> assignRole(@PathVariable UUID userId , @PathVariable UUID roleId){

        return ResponseEntity.ok(userRoleService.assignRole(userId , roleId));
    }

    @DeleteMapping("/{userId}/remove/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Boolean> removeRole(
            @PathVariable UUID userId,
            @PathVariable UUID roleId
    ){
        return ResponseEntity.ok(userRoleService.removeRoleFromUser(userId,roleId));
    }
}
