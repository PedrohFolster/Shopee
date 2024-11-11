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

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<?> getProdutosByLojaId(@PathVariable Long lojaId, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        try {
            List<ProdutoDTO> produtos = produtoService.getProdutosByLojaIdAndUsuario(lojaId, usuarioId);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoDTO>> getProdutosAtivos() {
        List<ProdutoDTO> produtosAtivos = produtoService.getProdutosAtivos();
        return ResponseEntity.ok(produtosAtivos);
    }

    @PostMapping("/finalizar-compra")
    public ResponseEntity<?> finalizarCompra(@RequestBody List<ProdutoDTO> produtos) {
        try {
            for (ProdutoDTO produto : produtos) {
                produtoService.reduzirEstoque(produto.getId(), 1);
            }
            return ResponseEntity.ok("Compra finalizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/categoria/{categoriaProdutoId}")
    public ResponseEntity<List<ProdutoDTO>> getProdutosByCategoriaProdutoId(@PathVariable Long categoriaProdutoId) {
        List<ProdutoDTO> produtos = produtoService.getProdutosByCategoriaProdutoId(categoriaProdutoId);
        return ResponseEntity.ok(produtos);
    }
}