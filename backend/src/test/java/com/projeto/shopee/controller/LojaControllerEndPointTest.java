package com.projeto.shopee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class LojaControllerEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private LojaDTO novaLoja;
    private UsuarioDTO novoUsuario;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        // Limpa os repositórios antes de cada teste
        lojaRepository.deleteAll();
        usuarioRepository.deleteAll();

        // Configuração do usuário
        novoUsuario = new UsuarioDTO();
        novoUsuario.setNome("Usuário Teste");
        novoUsuario.setEmail("teste@teste.com");
        novoUsuario.setTelefone("48988613333");
        novoUsuario.setCpf("13164097913");
        novoUsuario.setDataNascimento(LocalDate.now().minusYears(13));

        EnderecoDTO novoEnderecoDTO = new EnderecoDTO();
        novoEnderecoDTO.setCep("88075520");
        novoEnderecoDTO.setRua("Rua Exemplo");
        novoEnderecoDTO.setNumero("123");
        novoEnderecoDTO.setCidade("Cidade Exemplo");
        novoEnderecoDTO.setEstado("Estado Exemplo");
        novoEnderecoDTO.setPais("País Exemplo");
        novoUsuario.setEnderecoDTO(novoEnderecoDTO);

        UsuarioAutenticarDTO novoUsuarioAutenticar = new UsuarioAutenticarDTO();
        novoUsuarioAutenticar.setUsername("teste@teste.com");
        novoUsuarioAutenticar.setPasswordHash("Senha123!");
        novoUsuario.setUsuarioAutenticarDTO(novoUsuarioAutenticar);

        // Configuração da loja
        novaLoja = new LojaDTO();
        novaLoja.setNome("Loja Teste");
        novaLoja.setCategoriaLojaId(1L); // Supondo que a categoria com ID 1 existe

        objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte para Java 8 Date/Time API

        // Configuração da sessão
        session = new MockHttpSession();
    }

    @Test
    void criarUsuarioECriarLoja() throws Exception {
        // Criar usuário
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);
        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());

        // Recuperar o usuário criado
        Usuario usuarioCriado = usuarioRepository.findByEmail(novoUsuario.getEmail());

        // Configurar a sessão com o ID do usuário criado
        session.setAttribute("userId", usuarioCriado.getId());
        novaLoja.setUsuarioId(usuarioCriado.getId());

        // Criar loja
        String lojaNovaJson = objectMapper.writeValueAsString(novaLoja);
        this.mockMvc.perform(post("/lojas")
                .session(session) // Adiciona a sessão ao request
                .contentType(MediaType.APPLICATION_JSON)
                .content(lojaNovaJson))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveCriarLojaSeUsuarioJaPossui() throws Exception {
        // Criar usuário
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);
        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());

        // Recuperar o usuário criado
        Usuario usuarioCriado = usuarioRepository.findByEmail(novoUsuario.getEmail());

        // Configurar a sessão com o ID do usuário criado
        session.setAttribute("userId", usuarioCriado.getId());
        novaLoja.setUsuarioId(usuarioCriado.getId());

        // Primeiro, cria a loja para o usuário
        Loja lojaExistente = new Loja();
        lojaExistente.setNome("Loja Existente");
        lojaExistente.setUsuario(usuarioCriado);
        lojaRepository.save(lojaExistente);

 
        String lojaNovaJson = objectMapper.writeValueAsString(novaLoja);
        this.mockMvc.perform(post("/lojas")
                .session(session) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(lojaNovaJson))
                .andExpect(status().isBadRequest());
    }
} 