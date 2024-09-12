package com.guilherme.demo.slices.image.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.demo.slices.image.entity.Image;
import com.guilherme.demo.slices.image.enums.ImageExtencion;
import com.guilherme.demo.slices.image.repository.ImageReporitory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService{


    private final ImageReporitory repository;

    
    @Transactional
    public Image save(Image image) {
       return repository.save(image);
    }

    
    public Optional<Image> findById(String id) {
        return repository.findById(id);
    }

    
    public List<Image> search(ImageExtencion extencion, String query) {
        return repository.findByExtencionAndNameOrTagsLike(extencion, query);
    }
    
}
