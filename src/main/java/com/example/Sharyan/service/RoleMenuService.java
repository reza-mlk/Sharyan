package com.example.Sharyan.service;


import com.example.Sharyan.dto.RoleMenuResponseDTO;
import com.example.Sharyan.entity.Menu;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.entity.RoleMenu;
import com.example.Sharyan.repository.MenuRepository;
import com.example.Sharyan.repository.RoleMenuRepository;
import com.example.Sharyan.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.StickyAssignor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleMenuService {

    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;


//    ===================== ASSIGN MENU TO ROLE =================================
    @Transactional
    public RoleMenuResponseDTO assignMenuToRole(UUID roleId , UUID menuId){

        if(roleMenuRepository.existsByRoleIdAndMenuId(roleId , menuId)){
            throw new RuntimeException("Menu already assigned to role");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRole(role);
        roleMenu.setMenu(menu);

        roleMenuRepository.save(roleMenu);

        Set<String> roleMenus =
                roleMenuRepository.findByRoleId(roleId)
                        .stream()
                        .map(rm -> rm.getMenu().getTitle())
                        .collect(Collectors.toSet());

        Set<String> menuRoles =
                roleMenuRepository.findByMenuId(menuId)
                        .stream()
                        .map(rm -> rm.getMenu().getName())
                        .collect(Collectors.toSet());

        return convertToResponse(role , menu , roleMenus , menuRoles);
    }

//    ==================== REMOVE MENU FROM ROLE =========================
    @Transactional
    public boolean removeMenuFromRole(UUID roleId , UUID menuId){

        if(!roleMenuRepository.existsByRoleIdAndMenuId(roleId ,menuId)){
            throw new RuntimeException("Role menu relation not found");
        }
        roleMenuRepository.deleteByRoleIdAndMenuId(roleId , menuId);
        return true;
    }


//    ======================== CONVERTS =======================
    private RoleMenuResponseDTO convertToResponse(
            Role role,
            Menu menu,
            Set<String> roleMenus,
            Set<String> menuRoles
    ){

        return RoleMenuResponseDTO.builder()
                .status(true)
                .message("Menu assigned successfully")
                .roleId(role.getId())
                .roleName(role.getName())
                .menuId(menu.getId())
                .menuTitle(menu.getTitle())
                .roleMenus(roleMenus)
                .menuRoles(menuRoles)
                .build();
    }

}
