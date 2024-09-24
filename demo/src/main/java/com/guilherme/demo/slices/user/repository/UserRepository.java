package com.guilherme.demo.slices.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guilherme.demo.slices.user.entity.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String>{

    User findByEmail(String email);

    
}
