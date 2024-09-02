package com.guilherme.demo.application;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.guilherme.demo.domain.entity.Image;
import com.guilherme.demo.domain.enums.ImageExtencion;

@Component
public class ImagesMapper {
   

    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return Image.builder()
                    .name(name)
                    .tags(String.join(",", tags))
                    .size(file.getSize())
                    .extencion(ImageExtencion.valueOf(MediaType.valueOf(file.getContentType())))
                    .file(file.getBytes())
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
