package com.projeto.shopee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.security.JwtService;

@RestController
@RequestMapping("/login")
public class LoginRequestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody UsuarioAutenticarDTO usuarioDTO) {
        System.out.println("Tentativa de login para o usuário: " + usuarioDTO.getLogin());

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getLogin(), usuarioDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtService.getGenereteToken(authentication);

            Long userId = jwtService.getUserIdFromToken(token);
            System.out.println("ID do usuário logado: " + userId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            response.put("token", token);
            response.put("userId", userId.toString());
            System.out.println("Token JWT gerado no login: " + token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Credenciais inválidas");
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }
    }
}