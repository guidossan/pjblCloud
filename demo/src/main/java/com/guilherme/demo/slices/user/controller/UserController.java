package com.guilherme.demo.slices.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.demo.slices.common.exception.DuplicatedTupleException;
import com.guilherme.demo.slices.common.jwt.AccessToken;
import com.guilherme.demo.slices.common.jwt.JwtService;
import com.guilherme.demo.slices.user.dto.CredentialDto;
import com.guilherme.demo.slices.user.dto.UserDTO;
import com.guilherme.demo.slices.user.entity.User;
import com.guilherme.demo.slices.user.mapper.UserMapper;
import com.guilherme.demo.slices.user.repository.UserRepository;
import com.guilherme.demo.slices.user.service.UserService;
import com.guilherme.demo.slices.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
 
 
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UserDTO dto){
        try {
            User user = mapper.maptoUser(dto);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
            
        } catch (DuplicatedTupleException e) {
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> autenticate(@RequestBody CredentialDto credentials) {
        
       
        var token = userService.authenticate(credentials.getEmail(), credentials.getSenha());
        
        if(token != null){
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
        //return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        
       
    }
    
}
