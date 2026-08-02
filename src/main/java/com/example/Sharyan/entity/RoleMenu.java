package com.example.Sharyan.entity;

import com.example.Sharyan.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "role_menu",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_menu",
                        columnNames = {"role_id" , "menu_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class RoleMenu extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "role_id" , nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "menu_id" , nullable = false)
    private Menu menu;
}
