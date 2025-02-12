package com.iuh.edu.fit.service;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;
import java.util.List;

public interface ServicesServiceImpl {
    ServicesResponse addService(ServicesRequest request);
    List<ServicesResponse> getAllServices();
    ServicesResponse getServiceById(String serviceId);
    ServicesResponse updateService(String serviceId, ServicesRequest request);
    void deleteService(String serviceId);
}
