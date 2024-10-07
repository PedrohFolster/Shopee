package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.util.UsuarioMapper;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

    private boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOs(usuarios);
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(usuarioMapper::toDTO).orElse(null);
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        if (!isEmailValid(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("O e-mail fornecido não é válido. Por favor, insira um e-mail correto.");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsById(id)) {
            return null;
        }
        if (!isEmailValid(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        usuarioDTO.setId(id);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}