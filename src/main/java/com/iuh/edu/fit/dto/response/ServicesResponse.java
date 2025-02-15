package com.iuh.edu.fit.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesResponse {
    String id;
    String serviceName;
    String description;
    double price;
    String categoryId;
    String locationId;
    String userId;
}
