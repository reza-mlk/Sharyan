package com.example.Sharyan.repository;

import com.example.Sharyan.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole , UUID> {

    boolean existsByUserIdAndRoleId(UUID userId , UUID roleId);

    void deleteByUserIdAndRoleId(UUID userId , UUID roleId);
}
