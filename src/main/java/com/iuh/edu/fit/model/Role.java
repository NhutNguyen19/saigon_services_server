package com.iuh.edu.fit.model;

import java.util.Set;

import com.iuh.edu.fit.enums.RoleName;
import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
