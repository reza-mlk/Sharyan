package com.example.Sharyan.service;

import com.example.Sharyan.dto.RoleRequestDTO;
import com.example.Sharyan.dto.RoleResponseDTO;
import com.example.Sharyan.dto.RoleUpdateRequestDTO;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.repository.MenuRepository;
import com.example.Sharyan.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

// =================== UPDATE ROLE ==========================
    @Transactional
    public RoleResponseDTO updateRole(UUID id , RoleUpdateRequestDTO requestDTO){

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(requestDTO.getName());
        role.setDescription(requestDTO.getDescription());
        role.setEnabled(requestDTO.getEnabled());

        Role updatedRole = roleRepository.save(role);

        return convertToRoleResponse(role);

    }


// ==================== GET ALL ROLES ==========================
    @Transactional
    public Set<RoleResponseDTO> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(this::convertToRoleResponse)
                .collect(Collectors.toSet());
    }

// ==================== DELETE ROLE ==========================
    @Transactional
    public boolean deleteRole(UUID roleId){

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if("SUPER_ADMIN".equals(role.getCode())){
            throw new RuntimeException("SUPER_ADMIN role cannot be deleted");
        }

        role.getPermissions().clear();
        roleRepository.delete(role);

        return true;
    }



//    ========================= CONVERTS =========================

    private RoleResponseDTO convertToRoleResponse(Role role){

        Set<String> menus =
                role.getRoleMenus()
                        .stream()
                        .map(roleMenu -> roleMenu.getMenu().getTitle())
                        .collect(Collectors.toSet());

        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .code(role.getCode())
                .enabled(role.isEnabled())
                .menus(menus)
                .build();
    }
}
