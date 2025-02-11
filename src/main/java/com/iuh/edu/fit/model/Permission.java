package com.iuh.edu.fit.model;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    private String id;

    private String permissionName;
    private String description;
}
