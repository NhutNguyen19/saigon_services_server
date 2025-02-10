package com.iuh.edu.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "business_owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOwner {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Service> services;

    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;
}
