package com.projeto.shopee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.dto.UsuarioDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    private UsuarioDTO novoUsuario;
    private EnderecoDTO novoEnderecoDTO;
    private UsuarioAutenticarDTO novoUsuarioAutenticar;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        novoUsuario = new UsuarioDTO();
        novoUsuario.setNome("Usuário Teste");
        novoUsuario.setEmail("teste@teste.com");
        novoUsuario.setTelefone("48988613333");
        novoUsuario.setCpf("13164097913");

        LocalDate dataNascimento = LocalDate.now().minusYears(13);
        novoUsuario.setDataNascimento(dataNascimento);

        novoEnderecoDTO = new EnderecoDTO();
        novoEnderecoDTO.setCep("88075520");
        novoEnderecoDTO.setRua("Rua Exemplo");
        novoEnderecoDTO.setNumero("123");
        novoEnderecoDTO.setCidade("Cidade Exemplo");
        novoEnderecoDTO.setEstado("Estado Exemplo");
        novoEnderecoDTO.setPais("País Exemplo");
        novoUsuario.setEnderecoDTO(novoEnderecoDTO);

        novoUsuarioAutenticar = new UsuarioAutenticarDTO();
        novoUsuarioAutenticar.setLogin("teste@teste.com");
        novoUsuarioAutenticar.setPassword("Senha123!");
        novoUsuarioAutenticar.setPerfil("Administrador");
        novoUsuario.setUsuarioAutenticarDTO(novoUsuarioAutenticar);

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void usuarioControllerMapEndPoint() throws Exception {
        this.mockMvc.perform(get("/usuarios")).andExpect(status().isOk());
    }

    @Test
    void criarUsuario() throws Exception {
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);

        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());
    }
}