package com.example.Sharian.config;

import com.example.Sharian.entity.Role;
import com.example.Sharian.repository.RoleRepository;
import com.example.Sharian.repository.UserRepository;
import com.example.Sharian.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){

        createRole(
                "Super Admin",
                "SUPER_ADMIN",
                "Full system access"
        );

        createRole(
                "Admin",
                "ADMIN",
                "System administrator"
        );

        createRole(
                "User",
                "USER",
                "Normal user"
        );
    }

    private void createRole(String name , String code , String description){

        if(roleRepository.existsByCode(code)){
            return;
        }

        Role role = new Role();

        role.setName(name);
        role.setCode(code);
        role.setDescription(description);
        role.setCreatedAt(LocalDateTime.now());
        roleRepository.save((role));
    }

}


