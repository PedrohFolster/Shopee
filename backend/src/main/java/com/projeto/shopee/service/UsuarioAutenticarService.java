package com.projeto.shopee.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;
import com.projeto.shopee.util.Hashing;

@Service
public class UsuarioAutenticarService {

    @Autowired
    private UsuarioAutenticarRepository usuarioAutenticarRepository;


public boolean authenticate(String username, String password) {
    UsuarioAutenticar usuarioAutenticar = usuarioAutenticarRepository.findByLogin(username).orElse(null);
    if (usuarioAutenticar != null) {
        return Hashing.matches(password, usuarioAutenticar.getPassword());
    }
    return false;
}

}
