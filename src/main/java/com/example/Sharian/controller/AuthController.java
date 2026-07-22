package com.example.Sharian.controller;


import com.example.Sharian.dto.RegisterRequestDTO;
import com.example.Sharian.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO requestDTO){

        userService.register(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully.");
    }
}
