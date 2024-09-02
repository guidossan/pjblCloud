package com.guilherme.demo.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilherme.demo.application.Jwt.JwtService;
import com.guilherme.demo.domain.AccessToken;
import com.guilherme.demo.domain.entity.User;
import com.guilherme.demo.domain.exception.DuplicatedTupleException;
import com.guilherme.demo.domain.service.UserService;
import com.guilherme.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
       
    }

    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if (possibleUser != null){
            throw new DuplicatedTupleException("Usuário ja existe");
        }
        /*
         * Encripta a senha antes de salvar no banco
         */
        encodePassword(user);
        return userRepository.save(user);
    }


    @Override
    public AccessToken authenticate(String email, String senha) {
        /*
         * se usuario existir cadastrado
         */
        var user = getByEmail(email);
        if (user == null){
            return null;
        }
        /*
         * verifica senha digitada com a do banco
         */
        boolean matches = passwordEncoder.matches(senha, user.getSenha());
        
        if (matches){
            /*
             * gera token de senha
             */
            return jwtService.generateToken(user);
        }
        return null;
    }



    private void encodePassword(User user){
        String rawPassword = user.getSenha();
        String encodePassword = passwordEncoder.encode(rawPassword);
        user.setSenha(encodePassword);
    }
    
}
