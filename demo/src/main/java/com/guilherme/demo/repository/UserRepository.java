package com.guilherme.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guilherme.demo.domain.entity.User;


public interface UserRepository extends JpaRepository<User, String>{

    User findByEmail(String email);

    
}
