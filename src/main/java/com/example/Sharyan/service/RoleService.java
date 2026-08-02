package com.example.Sharyan.service;

import com.example.Sharyan.dto.RoleRequestDTO;
import com.example.Sharyan.dto.RoleResponseDTO;
import com.example.Sharyan.entity.Menu;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.repository.MenuRepository;
import com.example.Sharyan.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

//    =================== CREATE ROLE ===========================
    public RoleResponseDTO createRole(RoleRequestDTO requestDTO){

        if(roleRepository.existsByCode(requestDTO.getCode())){
            throw new RuntimeException("Role already exists");
        }

        Role role = new Role();

        role.setName(requestDTO.getName());
        role.setCode(requestDTO.getCode());
        role.setDescription(requestDTO.getDescription());

        roleRepository.save(role);

        return convertToRoleResponse(role);
    }
// ==================== GET ALL ROLES ==========================
    public Set<RoleResponseDTO> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(this::convertToRoleResponse)
                .collect(Collectors.toSet());
    }



//    ========================= CONVERTS =========================

    private RoleResponseDTO convertToRoleResponse(Role role){

        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .enabled(role.isEnabled())
                .build();
    }
}
