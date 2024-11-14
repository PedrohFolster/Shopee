package com.projeto.shopee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.LoginRequestDTO;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.service.UsuarioAutenticarService;

@RestController
@RequestMapping("/login")
public class LoginRequestController {

    @Autowired
    private UsuarioAutenticarService usuarioAutenticarService;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        System.out.println("Tentativa de login para o usuário: " + loginRequest.getUsername());
        boolean isAuthenticated = usuarioAutenticarService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println("Autenticação bem-sucedida: " + isAuthenticated);

        if (isAuthenticated) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = jwtService.getGenereteToken(authentication);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            response.put("token", token);
            System.out.println("Token JWT gerado no login: " + token);
            return ResponseEntity.ok(response);
        } else {
            System.out.println("Credenciais inválidas");
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }
    }
}