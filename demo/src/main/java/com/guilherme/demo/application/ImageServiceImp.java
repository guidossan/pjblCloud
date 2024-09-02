package com.guilherme.demo.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.demo.domain.entity.Image;
import com.guilherme.demo.domain.enums.ImageExtencion;
import com.guilherme.demo.domain.service.ImageService;
import com.guilherme.demo.repository.ImageReporitory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService{


    private final ImageReporitory repository;

    @Override
    @Transactional
    public Image save(Image image) {
       return repository.save(image);
    }

    @Override
    public Optional<Image> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtencion extencion, String query) {
        return repository.findByExtencionAndNameOrTagsLike(extencion, query);
    }
    
}
