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
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.security.JwtService;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private JwtService jwtService;

    private ProdutoDTO novoProduto;
    private UsuarioDTO novoUsuario;
    private LojaDTO novaLoja;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
        lojaRepository.deleteAll();
        usuarioRepository.deleteAll();

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
        novoUsuarioAutenticar.setLogin("teste@teste.com");
        novoUsuarioAutenticar.setPassword("Senha123!");
        novoUsuarioAutenticar.setPerfil("Administrador");
        novoUsuario.setUsuarioAutenticarDTO(novoUsuarioAutenticar);

        novaLoja = new LojaDTO();
        novaLoja.setNome("Loja Teste");
        novaLoja.setCategoriaLojaId(1L);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void criarUsuarioLojaProduto() throws Exception {
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);
        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());

        Usuario usuarioCriado = usuarioRepository.findByEmail(novoUsuario.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuarioCriado.getEmail(), novoUsuario.getUsuarioAutenticarDTO().getPassword());

        String token = jwtService.getGenereteToken(authentication);

        novaLoja.setUsuarioId(usuarioCriado.getId());

        String lojaNovaJson = objectMapper.writeValueAsString(novaLoja);
        this.mockMvc.perform(post("/lojas")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(lojaNovaJson))
                .andExpect(status().isOk());

        novoProduto = new ProdutoDTO();
        novoProduto.setNome("Produto Teste");
        novoProduto.setDescricao("Descrição do Produto Teste");
        novoProduto.setCategoriaProdutoId(1L);
        novoProduto.setPreco(100.0);
        novoProduto.setEstoque(10);
        novoProduto.setLojaId(1L);
        novoProduto.setStatusId(1L);
        novoProduto.setImagem("imagem.jpg");

        String produtoNovoJson = objectMapper.writeValueAsString(novoProduto);
        this.mockMvc.perform(post("/produtos")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(produtoNovoJson))
                .andExpect(status().isOk());
    }
}
