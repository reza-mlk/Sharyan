package com.example.Sharyan.service;

import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.entity.User;
import com.example.Sharyan.entity.UserRole;
import com.example.Sharyan.repository.RoleRepository;
import com.example.Sharyan.repository.UserRepository;
import com.example.Sharyan.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;



//    =============== ASSIGN ROLE ===========================
    @Transactional
    public UserResponseDTO assignRole(UUID userId , UUID roleId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        boolean exists =
                user.getUserRoles()
                        .stream()
                        .anyMatch(userRole ->
                                userRole.getRole()
                                        .getId()
                                        .equals(roleId));

        if(exists){
            throw new RuntimeException("User already has this role");
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        user.getUserRoles().add(userRole);

        userRoleRepository.save(userRole);
        return convertToUserResponse(user);

    }

//    ====================== REMOVE RULE FROM USER ==========================
    @Transactional
    public boolean removeRoleFromUser(UUID userId , UUID roleId){

        if (!userRoleRepository.existsByUserIdAndRoleId(userId , roleId)){
            throw new RuntimeException("User Role relation not found");
        }

        userRoleRepository.deleteByUserIdAndRoleId(userId , roleId);
        return true;
    }


// ======================== CONVERTS ===============================
    private UserResponseDTO convertToUserResponse(User user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(
                        user.getUserRoles()
                                .stream()
                                .map(item ->
                                        item.getRole().getCode())
                                .collect(Collectors.toSet())

                )
                .build();
    }
}
