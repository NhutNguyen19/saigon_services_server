package com.iuh.edu.fit.dto.request;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequest {
    @Size(min = 6, message = "Nhập mật khẩu có tối thiểu 6 ký tự")
    String password;

    String phone;
}
