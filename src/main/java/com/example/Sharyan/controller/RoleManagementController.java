package com.example.Sharyan.controller;


import com.example.Sharyan.dto.RoleRequestDTO;
import com.example.Sharyan.dto.RoleResponseDTO;
import com.example.Sharyan.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/roles")
public class RoleManagementController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO requestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(requestDTO));
    }

    @GetMapping
    public ResponseEntity<Set<RoleResponseDTO>> getRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }


}
