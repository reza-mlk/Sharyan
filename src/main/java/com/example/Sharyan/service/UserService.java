package com.example.Sharyan.service;

import com.example.Sharyan.dto.RegisterRequestDTO;
import com.example.Sharyan.dto.RegisterResponseDTO;
import com.example.Sharyan.dto.UserResponseDTO;
import com.example.Sharyan.dto.UserUpdateRequestDTO;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.entity.User;
import com.example.Sharyan.entity.UserRole;
import com.example.Sharyan.repository.RoleRepository;
import com.example.Sharyan.repository.UserRepository;
import com.example.Sharyan.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

//    ===================== REGISTER =================================
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO){

        if(userRepository.existsByUsername(requestDTO.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(requestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setEmail(requestDTO.getEmail());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setPhoneNumber(requestDTO.getPhoneNumber());

        userRepository.save(user);

        Role defaultRole = roleRepository.findByCode("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));


        UserRole userRole = new UserRole();

        userRole.setUser(user);
        userRole.setRole(defaultRole);

        user.getUserRoles().add(userRole);

        userRoleRepository.save(userRole);

        return convertToRegisterResponse(user);


    }


//    ====================== ADMIN , SUPER_ADMIN =========================

// ============== UPDATE USER ==================
    @Transactional
    public  UserResponseDTO updateUser(UUID id , UserUpdateRequestDTO requestDTO){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")   );

        if(requestDTO.getEmail() != null){
            user.setEmail(requestDTO.getEmail());
        }
        if(requestDTO.getFirstName() != null){
            user.setFirstName(requestDTO.getFirstName());
        }

        if(requestDTO.getLastName() != null){
            user.setLastName(requestDTO.getLastName());
        }


        if(requestDTO.getPhoneNumber() != null){
            user.setPhoneNumber(requestDTO.getPhoneNumber());
        }
        if(requestDTO.getEnabled() != null){
            user.setEnabled(requestDTO.getEnabled());
        }

        User savedUser = userRepository.save(user);

        return convertToUserResponse(savedUser);


    }
// ============== USER DELETE =========
    @Transactional
    public void deleteUser(UUID id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }




// ============   GET ALL USERS ===============
    public List<UserResponseDTO> getAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }



//    ============================== USER =====================================


//    ============= GET MY PROFILE ============
    public UserResponseDTO getMyProfile(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToUserResponse(user);
    }

//    ============== UPDATE MY PROFILE ========= NO USAGE
    @Transactional
    public UserResponseDTO updateMyProfile(String username , UserUpdateRequestDTO requestDTO){

        User user  = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(requestDTO.getEmail() != null){
            user.setEmail(requestDTO.getEmail());
        }
        if(requestDTO.getFirstName() != null){
            user.setFirstName(requestDTO.getFirstName());
        }
        if(requestDTO.getLastName() != null){
            user.setLastName(requestDTO.getLastName());
        }
        if(requestDTO.getPhoneNumber() != null){
            user.setPhoneNumber(requestDTO.getPhoneNumber());
        }

        User savedUser = userRepository.save(user);

        return convertToUserResponse(savedUser);

    }




//    ========================= CONVERTS ===========================



    private RegisterResponseDTO convertToRegisterResponse(User user){
        List<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getCode())
                .toList();

        return new RegisterResponseDTO(
                user.getId(),
                user.getUsername(),
                roles
        );
    }

    private UserResponseDTO convertToUserResponse(User user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.isEnabled())
                .roles(user.getUserRoles()
                        .stream()
                        .map(userRole -> userRole.getRole().getCode())
                        .collect(Collectors.toSet())

                )

                .permissions(user.getUserRoles()
                        .stream()
                        .flatMap(userRole ->
                                userRole.getRole()
                                        .getPermissions()
                                        .stream()
                        )
                        .map(permission -> permission.getCode())
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
