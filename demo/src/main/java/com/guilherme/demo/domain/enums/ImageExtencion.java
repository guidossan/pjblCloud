package com.guilherme.demo.domain.enums;
import java.util.Arrays;
import org.springframework.http.MediaType;



import lombok.Getter;

public enum ImageExtencion {
    PNG(MediaType.IMAGE_PNG),
    GIF(MediaType.IMAGE_GIF),
    JPEG(MediaType.IMAGE_JPEG);

    @Getter
    private MediaType mediaType;
    
    ImageExtencion(MediaType mediaType){
        this.mediaType = mediaType;
    }
    public static ImageExtencion valueOf(MediaType mediaType){
        return Arrays.stream(values())
        .filter(ie -> mediaType.equals(mediaType))
        .findFirst().orElse(null);
    }

    public static ImageExtencion ofName(String name){
        return Arrays.stream(values())
        .filter(ie -> ie.name().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
    }
}
