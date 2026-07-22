package com.example.Sharian.entity;

import com.example.Sharian.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_role",
                        columnNames = {"user_id" , "role_id"}
                )
        }
)
@Setter
@Getter
@NoArgsConstructor
public class UserRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "role_id" , nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "assigned_by")
    private User assignedBy;
}
