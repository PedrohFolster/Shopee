package com.projeto.shopee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.dto.EnderecoDTO;

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
        novoUsuario.setTelefone("11987654321");
        novoUsuario.setCpf("12345678901");
        novoUsuario.setDataNascimento(new Date());

        novoEnderecoDTO = new EnderecoDTO();
        novoEnderecoDTO.setCep("12345678");
        novoEnderecoDTO.setRua("Rua Teste");
        novoEnderecoDTO.setNumero("123");
        novoEnderecoDTO.setCidade("Cidade Teste");
        novoEnderecoDTO.setEstado("SP");
        novoEnderecoDTO.setPais("Brasil");
        novoUsuario.setEnderecoDTO(novoEnderecoDTO);

        novoUsuarioAutenticar = new UsuarioAutenticarDTO();
        novoUsuarioAutenticar.setUsername("teste@teste.com");
        novoUsuarioAutenticar.setPasswordHash("Senha123!");
        novoUsuario.setUsuarioAutenticarDTO(novoUsuarioAutenticar);
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