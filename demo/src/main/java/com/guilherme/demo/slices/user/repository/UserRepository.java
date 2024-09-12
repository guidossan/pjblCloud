package com.guilherme.demo.slices.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.demo.slices.user.entity.User;


public interface UserRepository extends JpaRepository<User, String>{

    User findByEmail(String email);

    
}
