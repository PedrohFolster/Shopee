package com.projeto.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.shopee.dto.CategoriaProdutoDTO;
import com.projeto.shopee.service.CategoriaProdutoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias-p")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @GetMapping
    public List<CategoriaProdutoDTO> getAllCategorias() {
        return categoriaProdutoService.getAllCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaProdutoDTO categoria = categoriaProdutoService.getCategoriaById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaProdutoDTO> createCategoria(@RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        CategoriaProdutoDTO novaCategoria = categoriaProdutoService.createCategoria(categoriaProdutoDTO);
        return ResponseEntity.ok(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        CategoriaProdutoDTO categoriaAtualizada = categoriaProdutoService.updateCategoria(id, categoriaProdutoDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaProdutoService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
