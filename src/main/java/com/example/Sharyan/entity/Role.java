package com.example.Sharyan.entity;

import com.example.Sharyan.base.BaseEntity;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();


    @OneToMany(
            mappedBy = "role" ,
            cascade = CascadeType.ALL
    )
    private Set<RoleMenu> roleMenus = new HashSet<>();
}
