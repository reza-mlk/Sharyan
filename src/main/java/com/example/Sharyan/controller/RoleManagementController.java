package com.example.Sharyan.controller;


import com.example.Sharyan.dto.RoleRequestDTO;
import com.example.Sharyan.dto.RoleResponseDTO;
import com.example.Sharyan.dto.RoleUpdateRequestDTO;
import com.example.Sharyan.service.RoleService;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/roles")
public class RoleManagementController {

    private final RoleService roleService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO requestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(requestDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Set<RoleResponseDTO>> getRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/delete/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Boolean> deleteRole(@PathVariable UUID roleId){
        return ResponseEntity.ok(roleService.deleteRole(roleId));
    }

    @PutMapping("/update/{roleId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable UUID roleId ,
            @RequestBody RoleUpdateRequestDTO requestDTO
            ){

        return ResponseEntity.ok(roleService.updateRole(roleId , requestDTO));

    }




}
