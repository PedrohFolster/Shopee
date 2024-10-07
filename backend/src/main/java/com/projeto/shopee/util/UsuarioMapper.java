package com.projeto.shopee.util;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Endereco;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.entities.UsuarioAutenticar;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setCpf(usuario.getCpf()); // Mapeamento do CPF
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());

        if (usuario.getEndereco() != null) {
            usuarioDTO.setEnderecoDTO(new EnderecoDTO(
                usuario.getEndereco().getId(), 
                usuario.getEndereco().getCep(),
                usuario.getEndereco().getRua(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getEstado(),
                usuario.getEndereco().getPais(),
                usuario.getEndereco().getComplemento()
            ));
        }

        if (usuario.getUsuarioAutenticar() != null) {
            usuarioDTO.setUsuarioAutenticarDTO(new UsuarioAutenticarDTO(
                usuario.getUsuarioAutenticar().getId(), 
                usuario.getUsuarioAutenticar().getUsername(),
                usuario.getUsuarioAutenticar().getPasswordHash()
            ));
        }

        return usuarioDTO;
    }

    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setCpf(usuarioDTO.getCpf()); // Mapeamento do CPF
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());

        if (usuarioDTO.getEnderecoDTO() != null) {
            Endereco endereco = new Endereco();
            endereco.setId(usuarioDTO.getEnderecoDTO().getId());
            endereco.setCep(usuarioDTO.getEnderecoDTO().getCep());
            endereco.setRua(usuarioDTO.getEnderecoDTO().getRua());
            endereco.setNumero(usuarioDTO.getEnderecoDTO().getNumero());
            endereco.setCidade(usuarioDTO.getEnderecoDTO().getCidade());
            endereco.setEstado(usuarioDTO.getEnderecoDTO().getEstado());
            endereco.setPais(usuarioDTO.getEnderecoDTO().getPais());
            endereco.setComplemento(usuarioDTO.getEnderecoDTO().getComplemento());
            usuario.setEndereco(endereco);
        }

        if (usuarioDTO.getUsuarioAutenticarDTO() != null) {
            UsuarioAutenticar usuarioAutenticar = new UsuarioAutenticar();
            usuarioAutenticar.setId(usuarioDTO.getUsuarioAutenticarDTO().getId());
            usuarioAutenticar.setUsername(usuarioDTO.getUsuarioAutenticarDTO().getUsername());
            try {
                usuarioAutenticar.setPasswordHash(usuarioDTO.getUsuarioAutenticarDTO().getPasswordHash());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            usuario.setUsuarioAutenticar(usuarioAutenticar);
        }

        return usuario;
    }

    public List<UsuarioDTO> toDTOs(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
    }
}