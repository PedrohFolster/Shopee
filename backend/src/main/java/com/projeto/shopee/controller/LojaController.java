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

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.service.LojaService;

import jakarta.servlet.http.HttpSession;
 
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
    public ResponseEntity<?> createLoja(@RequestBody LojaDTO lojaDTO, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        lojaDTO.setUsuarioId(usuarioId);
        try {
            LojaDTO novaLoja = lojaService.createLoja(lojaDTO);
            return ResponseEntity.ok(novaLoja);
        } catch (UsuarioJaPossuiLojaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
 
    @GetMapping("/verificar-loja")
    public ResponseEntity<String> verificarLojaUsuario() {
        Long usuarioId = 1L; // ID fixo do usuário
        boolean possuiLoja = lojaService.usuarioPossuiLoja(usuarioId);
        if (possuiLoja) {
            return ResponseEntity.ok("Redirecionar para /minha-loja");
        } else {
            return ResponseEntity.ok("Redirecionar para /criar-loja");
        }
    }
}
 