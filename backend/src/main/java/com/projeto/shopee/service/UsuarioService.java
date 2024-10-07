package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.util.UsuarioMapper;
import com.projeto.shopee.util.ValidationUtils;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOs(usuarios);
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(usuarioMapper::toDTO).orElse(null);
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        validateUsuario(usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        validateUsuario(usuarioDTO);
        usuarioDTO.setId(id);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private void validateUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNome() == null || usuarioDTO.getNome().isEmpty() || usuarioDTO.getNome().split(" ").length < 2) {
            throw new IllegalArgumentException("Nome completo é obrigatório e deve conter pelo menos dois nomes");
        }
        if (usuarioDTO.getEmail() == null || !usuarioDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        if (usuarioDTO.getTelefone() == null || !usuarioDTO.getTelefone().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        if (usuarioDTO.getCpf() == null || !ValidationUtils.isValidCpf(usuarioDTO.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if (usuarioDTO.getDataNascimento() == null || !ValidationUtils.isOlderThan12Years(usuarioDTO.getDataNascimento())) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória e o usuário deve ter mais de 12 anos");
        }
        if (usuarioDTO.getUsuarioAutenticarDTO() == null || !ValidationUtils.isValidPassword(usuarioDTO.getUsuarioAutenticarDTO().getPasswordHash())) {
            throw new IllegalArgumentException("Senha inválida. Deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial");
        }
        if (usuarioDTO.getEnderecoDTO() == null) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }
        if (usuarioDTO.getEnderecoDTO().getCep() == null || usuarioDTO.getEnderecoDTO().getCep().length() != 8) {
            throw new IllegalArgumentException("CEP inválido");
        }
        if (usuarioDTO.getEnderecoDTO().getRua() == null || usuarioDTO.getEnderecoDTO().getRua().isEmpty()) {
            throw new IllegalArgumentException("Rua é obrigatória");
        }
        if (usuarioDTO.getEnderecoDTO().getNumero() == null || usuarioDTO.getEnderecoDTO().getNumero().isEmpty()) {
            throw new IllegalArgumentException("Número é obrigatório");
        }
        if (usuarioDTO.getEnderecoDTO().getCidade() == null || usuarioDTO.getEnderecoDTO().getCidade().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        if (usuarioDTO.getEnderecoDTO().getEstado() == null || usuarioDTO.getEnderecoDTO().getEstado().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório");
        }
        if (usuarioDTO.getEnderecoDTO().getPais() == null || usuarioDTO.getEnderecoDTO().getPais().isEmpty()) {
            throw new IllegalArgumentException("País é obrigatório");
        }
    }
}