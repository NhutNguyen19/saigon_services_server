package com.iuh.edu.fit.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iuh.edu.fit.model.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGetResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String email;

    @JsonIgnore
    String password;

    String phone;

    @JsonIgnore
    List<Role> roles;

    @JsonIgnore
    List<Review> reviews;

    @JsonIgnore
    List<Favorite> favorites;

    @JsonIgnore
    List<Appointment> appointments;

    @JsonIgnore
    List<Transaction> transactions;

    @JsonIgnore
    List<Services> services;
}
