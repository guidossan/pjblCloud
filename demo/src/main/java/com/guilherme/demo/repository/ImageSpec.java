package com.guilherme.demo.repository;

import org.springframework.data.jpa.domain.Specification;

import com.guilherme.demo.domain.entity.Image;
import com.guilherme.demo.domain.enums.ImageExtencion;

public class ImageSpec {
    private ImageSpec(){}
    public static Specification<Image> extencionEqual(ImageExtencion extencion){
        return  (root, q, cb) -> cb.equal(root.get("extencion"), extencion);
    }
    public static Specification<Image> nameLike(String name){
        return  (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%"+name.toUpperCase()+"%");
    }
    public static Specification<Image> tagsLike(String tags){
        return  (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%"+tags.toUpperCase()+"%");
    }
}
