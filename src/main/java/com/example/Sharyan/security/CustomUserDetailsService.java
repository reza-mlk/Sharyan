package com.example.Sharyan.security;

import com.example.Sharyan.entity.User;
import com.example.Sharyan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> authorities = new ArrayList<>();

        user.getUserRoles()
                .forEach(userRole -> {
                    authorities.add(userRole.getRole().getCode());

                    userRole.getRole().getPermissions()
                            .forEach(permission ->
                                    authorities.add(permission.getCode()));
                });






        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities.toArray(String[]::new))
                .build();
    }
}
