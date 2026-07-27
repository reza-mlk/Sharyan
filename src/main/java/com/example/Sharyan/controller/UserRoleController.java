package com.example.Sharyan.controller;

import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.repository.UserRoleRepository;
import com.example.Sharyan.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserResponseDTO> assignRole(@PathVariable UUID userId , @PathVariable UUID roleId){

        return ResponseEntity.ok(userRoleService.assignRole(userId , roleId));
    }
}
