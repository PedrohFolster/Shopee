package com.projeto.shopee.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioAutenticarRepository usuarioAutenticarRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioAutenticar usuario = usuarioAutenticarRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(),
            usuario.getPasswordHash(),
            List.of(new SimpleGrantedAuthority("ROLE_USER")) // Ajuste as roles conforme necessário
        );
    }
}