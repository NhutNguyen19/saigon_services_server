package com.iuh.edu.fit.dto.request;

import com.iuh.edu.fit.model.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
}
