package com.iuh.edu.fit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iuh.edu.fit.dto.response.ImageResponse;

public interface ImageService {

    ImageResponse uploadImage(MultipartFile file, String serviceId);

    List<ImageResponse> getAllImages();

    ImageResponse getImageById(String id);

    void deleteImage(String id);
}
