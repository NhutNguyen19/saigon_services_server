package com.iuh.edu.fit.model;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String roleName;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
