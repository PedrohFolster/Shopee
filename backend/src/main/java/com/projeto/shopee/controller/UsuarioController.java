package com.projeto.shopee.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO novoUsuario = usuarioService.createUsuario(usuarioDTO);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioAtualizado = usuarioService.updateUsuario(id, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> getPerfilUsuario(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.getUserIdFromToken(token.substring(7));
        try {
            UsuarioDTO usuario = usuarioService.getUsuarioById(userId);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/validar-senha")
    public ResponseEntity<?> validarSenha(@RequestHeader("Authorization") String token, @RequestParam String senha) {
        Long userId = jwtService.getUserIdFromToken(token.substring(7));
        try {
            boolean isValid = usuarioService.validarSenha(userId, senha);
            return ResponseEntity.ok().body(Map.of("valid", isValid));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao validar senha.");
        }
    }
}