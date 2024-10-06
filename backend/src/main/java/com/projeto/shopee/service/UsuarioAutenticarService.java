package com.projeto.shopee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.util.Hashing;
import java.security.NoSuchAlgorithmException;

@Service
public class UsuarioAutenticarService {

    @Autowired
    private UsuarioAutenticarRepository usuarioAutenticarRepository;

    public boolean authenticate(String username, String password) {
        UsuarioAutenticar usuarioAutenticar = usuarioAutenticarRepository.findByUsername(username);
        if (usuarioAutenticar != null) {
            try {
                String hashedPassword = Hashing.hash(password);
                return usuarioAutenticar.getPasswordHash().equals(hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
