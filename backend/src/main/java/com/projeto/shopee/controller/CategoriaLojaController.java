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

import com.projeto.shopee.dto.CategoriaLojaDTO;
import com.projeto.shopee.service.CategoriaLojaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias-l")
public class CategoriaLojaController {

    @Autowired
    private CategoriaLojaService categoriaLojaService;

    @GetMapping
    public List<CategoriaLojaDTO> getAllCategorias() {
        return categoriaLojaService.getAllCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaLojaDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaLojaDTO categoria = categoriaLojaService.getCategoriaById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaLojaDTO> createCategoria(@RequestBody CategoriaLojaDTO categoriaLojaDTO) {
        CategoriaLojaDTO novaCategoria = categoriaLojaService.createCategoria(categoriaLojaDTO);
        return ResponseEntity.ok(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaLojaDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaLojaDTO categoriaLojaDTO) {
        CategoriaLojaDTO categoriaAtualizada = categoriaLojaService.updateCategoria(id, categoriaLojaDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaLojaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}