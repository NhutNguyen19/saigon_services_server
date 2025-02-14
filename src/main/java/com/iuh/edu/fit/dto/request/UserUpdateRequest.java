package com.iuh.edu.fit.dto.request;

import com.iuh.edu.fit.model.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 6, message = "Nhập tài khoản có tối thiểu 6 ký tự")
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
    List<String> roles;
}
