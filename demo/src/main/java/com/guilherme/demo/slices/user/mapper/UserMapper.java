package com.guilherme.demo.slices.user.mapper;

import org.springframework.stereotype.Component;

import com.guilherme.demo.slices.user.dto.UserDTO;
import com.guilherme.demo.slices.user.entity.User;

@Component
public class UserMapper {
    public User maptoUser(UserDTO dto){
        return User.builder().email(dto.getEmail()).name(dto.getName()).senha(dto.getSenha()).build();
    }
}
