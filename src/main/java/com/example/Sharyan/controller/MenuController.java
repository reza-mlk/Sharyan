package com.example.Sharyan.controller;

import com.example.Sharyan.dto.MenuRequestDTO;
import com.example.Sharyan.dto.MenuResponseDTO;
import com.example.Sharyan.service.MenuService;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<MenuResponseDTO> createMenu(@RequestBody MenuRequestDTO requestDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuService.createMenu(requestDTO));
    }


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<MenuResponseDTO>> getAllMenus(){
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteMenu(@PathVariable UUID id){
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<MenuResponseDTO> updateMenu(@PathVariable UUID id , @RequestBody MenuRequestDTO requestDTO){
        return ResponseEntity.ok(menuService.updateMenu(id , requestDTO));
    }

}
