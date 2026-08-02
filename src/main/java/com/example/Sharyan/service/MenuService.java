package com.example.Sharyan.service;

import com.example.Sharyan.dto.MenuRequestDTO;
import com.example.Sharyan.dto.MenuResponseDTO;
import com.example.Sharyan.entity.Menu;
import com.example.Sharyan.repository.MenuRepository;
import com.example.Sharyan.repository.RoleMenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;

// ================ CREATE MENU ==================
    @Transactional
    public MenuResponseDTO createMenu(MenuRequestDTO requestDTO){

        Menu menu = new Menu();

        menu.setTitle(requestDTO.getTitle());
        menu.setName(requestDTO.getName());
        menu.setRoute(requestDTO.getRoute());
        menu.setIcon(requestDTO.getIcon());
        menu.setSortOrder(requestDTO.getSortOrder());
        menu.setEnabled(requestDTO.isEnabled());

        if(requestDTO.getParentId() != null){
             Menu parent = menuRepository.findById(requestDTO.getParentId())
                     .orElseThrow(() -> new RuntimeException("Parent menu not found")   );

             menu.setParent(parent);
        }

        Menu savedMenu = menuRepository.save(menu);

        return convertToMenuResponse(savedMenu);

    }

//    ==================== GET ALL MENUS ==================
    public List<MenuResponseDTO> getAllMenus(){
        return menuRepository.findAll()
                .stream()
                .map(this::convertToMenuResponse)
                .toList();

    }

//    ===================== DELETE MENU ====================
    @Transactional
    public void deleteMenu(UUID id){

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if(!menu.getChildren().isEmpty()){
            throw new RuntimeException("Cannot delete menu with child menus");
        }

       menuRepository.deleteById(id);

        menuRepository.delete(menu);
    }

//    ==================== UPDATE MENU ==========================
    @Transactional
    public MenuResponseDTO updateMenu(UUID id , MenuRequestDTO requestDTO){

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));


        menu.setTitle(requestDTO.getTitle());
        menu.setName(requestDTO.getName());
        menu.setRoute(requestDTO.getRoute());
        menu.setIcon(requestDTO.getIcon());
        menu.setSortOrder(requestDTO.getSortOrder());
        menu.setEnabled(requestDTO.isEnabled());

        if(requestDTO.getParentId() != null){
            if(id.equals(requestDTO.getParentId())){
                throw new RuntimeException("Menu cannot be parent of itself");
            }

            Menu parent = menuRepository.findById(requestDTO.getParentId())
                    .orElseThrow( () -> new RuntimeException("Parent menu not found")   );

            menu.setParent(parent);
        }else {
            menu.setParent(null);
        }

        Menu updatedMenu = menuRepository.save(menu);
        return convertToMenuResponse(updatedMenu);



    }




//    ==================== CONVERTS =========================
    private MenuResponseDTO convertToMenuResponse(Menu menu){

        UUID parentId;
        if(menu.getParent() != null){
            parentId = menu.getParent().getId();
        }else{
            parentId = null;
        }

        return MenuResponseDTO.builder()
                .id(menu.getId())
                .title(menu.getTitle())
                .name(menu.getName())
                .route(menu.getRoute())
                .icon(menu.getIcon())
                .sortOrder(menu.getSortOrder())
                .enabled(menu.isEnabled())
                .parentId(parentId)
                .children(
                        menu.getChildren()
                                .stream()
                                .map(this::convertToMenuResponse)
                                .toList()
                )
                .roles(
                        menu.getRoleMenus()
                                .stream()
                                .map(roleMenu -> roleMenu.getRole().getCode())
                                .collect(Collectors.toSet())
                )
                .build();


    }
}
