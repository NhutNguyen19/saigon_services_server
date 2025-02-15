package com.iuh.edu.fit.service;

import java.util.List;

import com.iuh.edu.fit.dto.request.ServicesRequest;
import com.iuh.edu.fit.dto.response.ServicesResponse;

public interface ServicesService {
    ServicesResponse addService(ServicesRequest request);

    List<ServicesResponse> getAllServices();

    ServicesResponse getServiceById(String serviceId);

    ServicesResponse updateService(String serviceId, ServicesRequest request);

    void deleteService(String serviceId);

    ServicesResponse getServiceByName(String serviceName);
}
