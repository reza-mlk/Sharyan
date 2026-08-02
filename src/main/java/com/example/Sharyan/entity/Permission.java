package com.example.Sharyan.entity;

import com.example.Sharyan.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission extends BaseEntity {



    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false ,unique = true)
    private String code;

    private String description;

    @Column(nullable = false)
    private boolean enabled = true;
}
