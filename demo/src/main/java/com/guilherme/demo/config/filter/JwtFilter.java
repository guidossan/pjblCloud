package com.guilherme.demo.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.guilherme.demo.application.Jwt.InvalidTokenException;
import com.guilherme.demo.application.Jwt.JwtService;
import com.guilherme.demo.domain.entity.User;
import com.guilherme.demo.domain.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter{


    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        String token = getToken(request);
    
        if (token != null){
            try{
    
                String email = jwtService.getEmailFromToken(token);
                User user = userService.getByEmail(email);
                setUserAsAutheticated(user);
            }catch (InvalidTokenException e){
                log.error("Token inválido", e.getMessage());
            }catch(Exception e){
                log.error("Erro na validação do token", e.getMessage());
                
            }
        }
    
        filterChain.doFilter(request, response);
    }
        
    private void setUserAsAutheticated(User user){
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getSenha())
                .roles("USER")
                .build();
                
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }   
    private String getToken(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        if(auth != null){
            String[] authParts = auth.split(" ");
            if(authParts.length == 2){
                return authParts[1];
            }
        }
        return null;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        
        return request.getRequestURI().contains("/users");
    }
}
    

