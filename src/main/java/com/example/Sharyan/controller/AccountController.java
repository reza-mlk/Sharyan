package com.example.Sharyan.controller;

import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getAccount(Authentication authentication){

        String username = authentication.getName();

        return ResponseEntity.ok(userService.getAccount(username));
    }
}
