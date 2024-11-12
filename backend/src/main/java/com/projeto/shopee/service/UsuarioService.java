package com.projeto.shopee.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.util.Hashing;
import com.projeto.shopee.util.UsuarioMapper;
import com.projeto.shopee.util.ValidationUtils;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioAutenticarRepository usuarioAutenticarRepository;

    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOs(usuarios);
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(usuarioMapper::toDTO).orElse(null);
    }

    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        validateUsuario(usuarioDTO, true);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        // Valida os dados do usuário, sem validar a senha
        validateUsuario(usuarioDTO, false);

        // Verifica se o usuário existe
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualiza apenas os campos necessários
        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setDataNascimento(usuarioDTO.getDataNascimento());

        // Salva as alterações no banco de dados
        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(usuarioExistente);
    }

    public Long findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null ? usuario.getId() : null;
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean validarSenha(Long userId, String senha) throws NoSuchAlgorithmException {
        Optional<UsuarioAutenticar> usuarioAutenticarOpt = usuarioAutenticarRepository.findById(userId);
        if (usuarioAutenticarOpt.isPresent()) {
            UsuarioAutenticar usuarioAutenticar = usuarioAutenticarOpt.get();
            String hashedSenha = Hashing.hash(senha);
            return usuarioAutenticar.getPasswordHash().equals(hashedSenha);
        }
        return false;
    }

    private void validateUsuario(UsuarioDTO usuarioDTO, boolean validatePassword) {
        if (!ValidationUtils.isValidNomeCompleto(usuarioDTO.getNome())) {
            throw new IllegalArgumentException("Nome completo é obrigatório e deve conter pelo menos dois nomes");
        }
        if (!ValidationUtils.isValidEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido backend");
        }
        if (!ValidationUtils.isValidTelefone(usuarioDTO.getTelefone())) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        if (validatePassword && (usuarioDTO.getCpf() == null || !ValidationUtils.isValidCpf(usuarioDTO.getCpf()))) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if (!ValidationUtils.isValidDataNascimento(usuarioDTO.getDataNascimento())) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória e o usuário deve ter mais de 12 anos");
        }
        if (validatePassword && (usuarioDTO.getUsuarioAutenticarDTO() == null || !ValidationUtils.isValidPassword(usuarioDTO.getUsuarioAutenticarDTO().getPasswordHash()))) {
            System.out.println("Senha recebida: " + usuarioDTO.getUsuarioAutenticarDTO().getPasswordHash());
            throw new IllegalArgumentException("Senha inválida. Deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial");
        }
        if (!ValidationUtils.isValidEndereco(usuarioDTO.getEnderecoDTO())) {
            throw new IllegalArgumentException("Endereço é obrigatório e deve ser válido");
        }
    }
}
