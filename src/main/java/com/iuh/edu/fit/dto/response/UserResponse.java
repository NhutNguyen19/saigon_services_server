package com.iuh.edu.fit.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iuh.edu.fit.model.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String email;
    @JsonIgnore
    String password;
    String phone;
    List<Role> roles;
    List<Review> reviews;
    List<Favorite> favorites;
    List<Appointment> appointments;
    List<Transaction> transactions;
    List<Services> services;
}
