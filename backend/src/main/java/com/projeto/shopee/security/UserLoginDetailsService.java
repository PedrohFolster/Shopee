package com.projeto.shopee.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.LoginRequestDTO;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;

@Service
public class UserLoginDetailsService implements UserDetailsService {

    private final UsuarioAutenticarRepository usuarioAutenticarRepository;

    public UserLoginDetailsService(UsuarioAutenticarRepository usuarioAutenticarRepository) {
        this.usuarioAutenticarRepository = usuarioAutenticarRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioAutenticar usuario = usuarioAutenticarRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return new UserAuthenticated(new LoginRequestDTO(usuario.getUsername(), usuario.getPasswordHash()), usuario.getId());
    }
}
