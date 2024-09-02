package com.guilherme.demo.application;

import org.springframework.stereotype.Component;

import com.guilherme.demo.domain.entity.User;

@Component
public class UserMapper {
    public User maptoUser(UserDTO dto){
        return User.builder().email(dto.getEmail()).name(dto.getName()).senha(dto.getSenha()).build();
    }
}
