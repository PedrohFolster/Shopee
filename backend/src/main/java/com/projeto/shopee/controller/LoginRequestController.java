package com.projeto.shopee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.LoginRequestDTO;
import com.projeto.shopee.service.UsuarioAutenticarService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginRequestController {

    @Autowired
    private UsuarioAutenticarService usuarioAutenticarService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest, HttpSession session) {
        boolean isAuthenticated = usuarioAutenticarService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            // Retorna o ID da sessão
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            response.put("sessionId", session.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }
    }
}