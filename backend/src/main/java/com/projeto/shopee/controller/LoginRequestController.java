package com.projeto.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.shopee.service.UsuarioAutenticarService;
import com.projeto.shopee.dto.LoginRequestDTO;

@RestController
@RequestMapping("/login")
public class LoginRequestController {

    @Autowired
    private UsuarioAutenticarService usuarioAutenticarService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean isAuthenticated = usuarioAutenticarService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}