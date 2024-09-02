package com.guilherme.demo.domain.service;

import java.util.List;
import java.util.Optional;



import com.guilherme.demo.domain.entity.Image;
import com.guilherme.demo.domain.enums.ImageExtencion;

public interface ImageService {
    Image save(Image image);
    Optional<Image> findById(String id);
    
    List<Image> search (ImageExtencion extencion, String query);

}
