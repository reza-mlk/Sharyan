package com.example.Sharian.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test(Authentication authentication){
        return "hello" + authentication.getName();
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String user(){
        return "user area";
    }
}
