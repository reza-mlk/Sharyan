package com.example.Sharyan.controller;


import com.example.Sharyan.dto.LoginRequestDTO;
import com.example.Sharyan.dto.LoginResponseDTO;
import com.example.Sharyan.dto.RegisterRequestDTO;
import com.example.Sharyan.service.AuthService;
import com.example.Sharyan.service.UserService;
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
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO requestDTO){

        userService.register(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        return ResponseEntity.ok(authService.login(requestDTO));
    }
}
