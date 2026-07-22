package com.example.Sharian.entity;

import com.example.Sharian.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    @Column(nullable = false , unique = true)
    private String name;

    @Column(nullable = false , unique = true)
    private String code;

    private String description;

    @Column(nullable = false)
    private boolean enabled = true;


    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRole> userRoles = new HashSet<>();
}
