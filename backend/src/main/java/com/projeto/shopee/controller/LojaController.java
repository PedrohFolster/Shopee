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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.service.LojaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<LojaDTO> getAllLojas() {
        return lojaService.getAllLojas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLojaById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Long usuarioId = jwtService.getUserIdFromToken(token.substring(7));
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        LojaDTO loja = lojaService.getLojaById(id);
        if (loja == null) {
            return ResponseEntity.status(404).body("Loja não encontrada");
        }
        return ResponseEntity.ok(loja);
    }

    @GetMapping("/minha-loja")
    public ResponseEntity<?> getLojaByUsuario(@RequestHeader("Authorization") String token) {
        Long usuarioId = jwtService.getUserIdFromToken(token.substring(7));
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        LojaDTO loja = lojaService.getLojaByUsuarioId(usuarioId);
        if (loja == null) {
            return ResponseEntity.status(404).body("Loja não encontrada");
        }
        return ResponseEntity.ok(loja);
    }

    @PostMapping
    public ResponseEntity<?> createLoja(@RequestBody LojaDTO lojaDTO, @RequestHeader("Authorization") String token) {
        Long usuarioId = jwtService.getUserIdFromToken(token.substring(7));
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
    public ResponseEntity<String> verificarLojaUsuario(@RequestHeader("Authorization") String token) {
        Long usuarioId = jwtService.getUserIdFromToken(token.substring(7));
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        boolean possuiLoja = lojaService.usuarioPossuiLoja(usuarioId);
        if (possuiLoja) {
            return ResponseEntity.ok("Redirecionar para /minha-loja");
        } else {
            return ResponseEntity.ok("Redirecionar para /criar-loja");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LojaDTO>> searchLojasByName(@RequestParam String nome) {
        List<LojaDTO> lojas = lojaService.searchLojasByName(nome);
        return ResponseEntity.ok(lojas);
    }
}