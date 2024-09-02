package com.guilherme.demo.domain.service;

import com.guilherme.demo.domain.AccessToken;
import com.guilherme.demo.domain.entity.User;

public interface UserService {
    User getByEmail(String email);   
    User save(User user);
    AccessToken authenticate(String email, String senha);

} 