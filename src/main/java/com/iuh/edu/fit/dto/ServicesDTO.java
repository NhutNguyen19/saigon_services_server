package com.iuh.edu.fit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicesDTO {
    private String id;
    private String serviceName;
    private String description;
    private double price;
    private String categoryId;
    private String locationId;
    private String userId;
}
