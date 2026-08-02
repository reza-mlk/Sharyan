package com.example.Sharyan.repository;

import com.example.Sharyan.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu , UUID> {

    boolean existsByRoleIdAndMenuId(UUID roleId , UUID menuId);


    void deleteByRoleIdAndMenuId( UUID roleId ,UUID menuId);

    List<RoleMenu> findByRoleId(UUID roleId);

    List<RoleMenu> findByMenuId(UUID menuId);

}
