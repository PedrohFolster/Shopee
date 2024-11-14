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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.service.ProdutoService;

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
    public ResponseEntity<?> createProduto(@RequestBody ProdutoDTO produtoDTO,
            @RequestHeader("Authorization") String token) {
        try {

            Long userId = jwtService.getUserIdFromToken(token.substring(7));
            System.out.println("ID do usuário logado: " + userId);

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

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<?> getProdutosByLojaId(@PathVariable Long lojaId,
            @RequestHeader("Authorization") String token) {
        try {
            
            Long userId = jwtService.getUserIdFromToken(token.substring(7)); 
            List<ProdutoDTO> produtos = produtoService.getProdutosByLojaIdAndUsuario(lojaId, userId);
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
    public ResponseEntity<?> finalizarCompra(@RequestBody List<ProdutoDTO> produtos,
            @RequestHeader("Authorization") String token) {
        try {
            
            // Long userId = jwtService.getUserIdFromToken(token.substring(7)); 
           
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