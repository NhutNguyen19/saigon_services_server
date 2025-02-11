package com.iuh.edu.fit.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;

    private String roleName;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RolePermission> rolePermissions;
}
