package com.guilherme.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.guilherme.demo.application.Jwt.JwtService;
import com.guilherme.demo.config.filter.JwtFilter;
import com.guilherme.demo.domain.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UserService userService){
        return new JwtFilter(jwtService, userService);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        return http
            .cors(cors -> cors.configure(http))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	    config.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        /*
         * aqui define especificamente quais url poderam acessar
         */
        cors.registerCorsConfiguration("/**", config);
        return cors;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


 
}
