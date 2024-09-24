package com.guilherme.demo.slices.image.mapper;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.guilherme.demo.slices.image.dto.ImageDTO;
import com.guilherme.demo.slices.image.entity.Image;
import com.guilherme.demo.slices.image.enums.ImageExtencion;

@Component
public class ImagesMapper {
   

    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return Image.builder()
                    .name(name)
                    .tags(String.join(",", tags))
                    .size(file.getSize())
                    .extencion(ImageExtencion.valueOf(MediaType.valueOf(file.getContentType())))
                    .imageFileName(file.getBytes())
                    .build();
        
    }
    public ImageDTO imageToDto(Image image, String url){
        return ImageDTO.builder()
                .url(url)
                .extencion(image.getExtencion().name())
                .name(image.getName())
                .size(image.getSize())
                .uploadDate(image.getUploadTime().toLocalDate())
                .build();
    }
}
