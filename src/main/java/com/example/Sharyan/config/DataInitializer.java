package com.example.Sharyan.config;

import com.example.Sharyan.entity.Permission;
import com.example.Sharyan.entity.Role;
import com.example.Sharyan.repository.PermissionRepository;
import com.example.Sharyan.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Override
    public void run(String... args) {


        Permission userView = createPermission(
                "مشاهده کاربران",
                "USER_VIEW",
                "Can view users"
        );


        Permission userDelete = createPermission(
                "حذف کاربران",
                "USER_DELETE",
                "Can delete users"
        );


        Permission roleManage = createPermission(
                "مدیریت نقش ها",
                "ROLE_MANAGE",
                "Can manage roles"
        );


        Role superAdmin = createRole(
                "Super Admin",
                "SUPER_ADMIN",
                "Full system access"
        );


        Role admin = createRole(
                "Admin",
                "ADMIN",
                "System administrator"
        );


        Role user = createRole(
                "User",
                "USER",
                "Normal user"
        );


        superAdmin.getPermissions().add(userView);
        superAdmin.getPermissions().add(userDelete);
        superAdmin.getPermissions().add(roleManage);


        admin.getPermissions().add(userView);


        roleRepository.save(superAdmin);
        roleRepository.save(admin);
        roleRepository.save(user);

    }


    private Role createRole(
            String name,
            String code,
            String description
    ){

        if(roleRepository.existsByCode(code)){

            return roleRepository.findByCode(code)
                    .orElseThrow();

        }


        Role role = new Role();

        role.setName(name);
        role.setCode(code);
        role.setDescription(description);
        role.setCreatedAt(LocalDateTime.now());

        return roleRepository.save(role);
    }



    private Permission createPermission(
            String name,
            String code,
            String description
    ){

        if(permissionRepository.existsByCode(code)){

            return permissionRepository.findByCode(code)
                    .orElseThrow();

        }


        Permission permission = new Permission();

        permission.setName(name);
        permission.setCode(code);
        permission.setDescription(description);
        permission.setEnabled(true);
        permission.setCreatedAt(LocalDateTime.now());

        return permissionRepository.save(permission);
    }

}