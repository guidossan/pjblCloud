package com.guilherme.demo.repository;
import org.springframework.data.jpa.domain.Specification;


public class GenericSpec {
    
    private GenericSpec(){}

    public static <T> Specification<T> conjunction(){
        return (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}
