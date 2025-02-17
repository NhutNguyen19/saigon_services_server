package com.iuh.edu.fit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.iuh.edu.fit.dto.request.ImageRequest;
import com.iuh.edu.fit.dto.response.ImageResponse;
import com.iuh.edu.fit.model.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "service", ignore = true)
    Image toImage(ImageRequest request);

    @Mapping(target = "serviceId", source = "service.id")
    ImageResponse toImageResponse(Image image);

    void updateImage(@MappingTarget Image image, ImageRequest request);
}
