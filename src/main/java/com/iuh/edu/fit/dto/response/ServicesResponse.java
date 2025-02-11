package com.iuh.edu.fit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServicesResponse {
    private String id;
    private String serviceName;
    private String description;
    private double price;
    private String categoryId;
    private String locationId;
    private String userId;
}
