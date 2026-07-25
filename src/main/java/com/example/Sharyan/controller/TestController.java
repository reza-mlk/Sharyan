package com.example.Sharyan.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/view")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public String view(){

        return "You can view users";
    }


    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public String delete(){

        return "You can delete users";
    }

}