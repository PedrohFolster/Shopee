package com.projeto.shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.service.ProdutoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<ProdutoDTO> getAllProdutos() {
        return produtoService.getAllProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.getProdutoById(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<?> createProduto(@RequestBody ProdutoDTO produtoDTO, HttpServletRequest request) {
        try {
            // Obtenha o token JWT do cabeçalho Authorization
            String token = request.getHeader("Authorization").substring(7); // Remove "Bearer "

            // Decodifique o token para obter o ID do usuário
            Long userId = jwtService.getUserIdFromToken(token);
            System.out.println("ID do usuário logado: " + userId);

            // Passe o ID do usuário para o serviço
            ProdutoDTO novoProduto = produtoService.createProduto(produtoDTO, userId);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.updateProduto(id, produtoDTO);
        if (produtoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoDTO>> getProdutosAtivos() {
        List<ProdutoDTO> produtosAtivos = produtoService.getProdutosAtivos();
        return ResponseEntity.ok(produtosAtivos);
    }
}