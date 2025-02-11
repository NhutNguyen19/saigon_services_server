package com.iuh.edu.fit.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference; // ✅ Import Jackson

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private String id;

    private String categoryName;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // ✅ Tránh lỗi vòng lặp khi serialize JSON
    private List<Services> services;
}
