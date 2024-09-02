package com.guilherme.demo.application.Jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.guilherme.demo.domain.AccessToken;
import com.guilherme.demo.domain.entity.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKeyGenerator keyGenerator;
    public AccessToken generateToken(User user){

        var key = keyGenerator.getKey();
        var expirationDate  =generateExpirateDate();
        var claims = generateTokenClaims(user);

        String token = Jwts
                .builder()
                .signWith(key)
                .subject(user.getEmail())
                .expiration(expirationDate)
                .claims(claims)
                .compact();

        return new AccessToken(token);
    }
    private Date generateExpirateDate(){
        var expirationMinutes = 60;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutes);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }
    private Map<String, Object> generateTokenClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        return claims;
    }
    public String getEmailFromToken(String tokenJWT){
        try{
            return Jwts.parser()
                            .verifyWith(keyGenerator.getKey())
                            .build()
                            .parseSignedClaims(tokenJWT)
                            .getPayload()
                            .getSubject();

        }catch(JwtException e){
            throw new InvalidTokenException(e.getMessage());

        }
    }
}
