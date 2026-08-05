package com.example.Sharyan.controller;

import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.dto.UserUpdateRequestDTO;
import com.example.Sharyan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id , @RequestBody UserUpdateRequestDTO requestDTO){

        return ResponseEntity.ok(userService.updateUser(id , requestDTO));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser (@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
