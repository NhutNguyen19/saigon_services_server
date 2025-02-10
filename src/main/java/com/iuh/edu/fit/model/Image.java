package com.iuh.edu.fit.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    private String id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
