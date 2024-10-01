package com.projeto.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.service.LojaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @GetMapping
    public List<LojaDTO> getAllLojas() {
        return lojaService.getAllLojas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaDTO> getLojaById(@PathVariable Long id) {
        LojaDTO loja = lojaService.getLojaById(id);
        return ResponseEntity.ok(loja);
    }

    @PostMapping
    public ResponseEntity<LojaDTO> createLoja(@RequestBody LojaDTO lojaDTO) {
        LojaDTO novaLoja = lojaService.createLoja(lojaDTO);
        return ResponseEntity.ok(novaLoja);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaDTO> updateLoja(@PathVariable Long id, @RequestBody LojaDTO lojaDTO) {
        LojaDTO lojaAtualizada = lojaService.updateLoja(id, lojaDTO);
        return ResponseEntity.ok(lojaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoja(@PathVariable Long id) {
        lojaService.deleteLoja(id);
        return ResponseEntity.noContent().build();
    }
}
