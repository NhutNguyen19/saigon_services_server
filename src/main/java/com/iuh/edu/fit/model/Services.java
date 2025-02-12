package com.iuh.edu.fit.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference; // ✅ Import Jackson

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String serviceName;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference //  Tránh lỗi vòng lặp khi serialize JSON
    private Category category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    //  Hàm tự động tạo ID nếu nó bị null
    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.trim().isEmpty()) {
            this.id = UUID.randomUUID().toString(); // Tạo ID mới nếu chưa có
        }
    }
}
