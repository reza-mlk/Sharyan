package com.example.Sharyan.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDTO {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
