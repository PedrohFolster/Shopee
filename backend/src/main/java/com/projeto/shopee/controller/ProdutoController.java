package com.projeto.shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.service.ProdutoService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

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
    public ResponseEntity<?> createProduto(@RequestBody ProdutoDTO produtoDTO, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        try {
            ProdutoDTO novoProduto = produtoService.createProduto(produtoDTO, usuarioId);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.updateProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<ProdutoDTO>> getProdutosByLojaId(@PathVariable Long lojaId) {
        List<ProdutoDTO> produtos = produtoService.getProdutosByLojaId(lojaId);
        return ResponseEntity.ok(produtos);
    }
}