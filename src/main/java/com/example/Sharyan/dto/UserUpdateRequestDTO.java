package com.example.Sharyan.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserUpdateRequestDTO {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Boolean enabled;
}
