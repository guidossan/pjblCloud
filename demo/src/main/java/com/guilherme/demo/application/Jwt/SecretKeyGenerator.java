package com.guilherme.demo.application.Jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@Component
public class SecretKeyGenerator {
    private SecretKey key;


    public SecretKey getKey(){
        
        if (key == null ){
            key = Jwts.SIG.HS256.key().build();
        }
        return key;
    }
}
