package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Endereco;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.UsuarioAutenticarRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.util.Hashing;
import com.projeto.shopee.util.UsuarioMapper;
import com.projeto.shopee.util.ValidationUtils;

import java.security.NoSuchAlgorithmException;

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
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        if (usuarioRepository.existsByCpf(usuarioDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já está em uso.");
        }

        validateUsuario(usuarioDTO, true);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO updateUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistentePorEmail = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if (usuarioExistentePorEmail != null && !usuarioExistentePorEmail.getId().equals(id)) {
            throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
        }

        validateUsuario(usuarioDTO, false);

        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setDataNascimento(usuarioDTO.getDataNascimento());

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

    public boolean validarSenha(Long userId, String senha) {
        Optional<UsuarioAutenticar> usuarioAutenticarOpt = usuarioAutenticarRepository.findById(userId);
        if (usuarioAutenticarOpt.isPresent()) {
            UsuarioAutenticar usuarioAutenticar = usuarioAutenticarOpt.get();
            return Hashing.matches(senha, usuarioAutenticar.getPassword());
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
        if (validatePassword && (usuarioDTO.getUsuarioAutenticarDTO() == null || !ValidationUtils.isValidPassword(usuarioDTO.getUsuarioAutenticarDTO().getPassword()))) {
            System.out.println("Senha recebida: " + usuarioDTO.getUsuarioAutenticarDTO().getPassword());
            throw new IllegalArgumentException("Senha inválida. Deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial");
        }
        if (!ValidationUtils.isValidEndereco(usuarioDTO.getEnderecoDTO())) {
            throw new IllegalArgumentException("Endereço é obrigatório e deve ser válido");
        }
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean cpfExiste(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    public void updateSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!validarSenha(usuario.getId(), senhaAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!ValidationUtils.isValidPassword(novaSenha)) {
            throw new IllegalArgumentException("Nova senha inválida. Deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial.");
        }

        UsuarioAutenticar usuarioAutenticar = usuario.getUsuarioAutenticar();
        try {
            usuarioAutenticar.setPassword(novaSenha);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao atualizar senha.");
        }
        usuarioAutenticarRepository.save(usuarioAutenticar);
    }

    public void updateEndereco(Long id, EnderecoDTO enderecoDTO, String senha) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!validarSenha(usuario.getId(), senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        if (!ValidationUtils.isValidEndereco(enderecoDTO)) {
            throw new IllegalArgumentException("Endereço inválido.");
        }

        Endereco endereco = usuario.getEndereco();
        endereco.setCep(enderecoDTO.getCep());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPais(enderecoDTO.getPais());
        endereco.setComplemento(enderecoDTO.getComplemento());

        usuarioRepository.save(usuario);
    }
}
