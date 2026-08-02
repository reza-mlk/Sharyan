package com.example.Sharyan.entity;

import com.example.Sharyan.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menus")
@Setter
@Getter
public class Menu extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false )
    private String route;

    private String icon;

    @Column(nullable = false)
    private Integer sortOrder;

    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @OneToMany(mappedBy = "parent")
    private Set<Menu> children = new HashSet<>();

    @OneToMany(
            mappedBy = "menu",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<RoleMenu> roleMenus = new HashSet<>();




}
