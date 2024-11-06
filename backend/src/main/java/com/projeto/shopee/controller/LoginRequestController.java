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
import com.projeto.shopee.security.SessionFilter;
import com.projeto.shopee.service.UsuarioAutenticarService;
import com.projeto.shopee.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
 
@RestController
@RequestMapping("/login")
public class LoginRequestController {
 
    @Autowired
    private UsuarioAutenticarService usuarioAutenticarService;
    @Autowired
    private UsuarioService usuarioService;
 
 
@PostMapping
public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
    System.out.println("Tentativa de login para o usuário: " + loginRequest.getUsername());
    boolean isAuthenticated = usuarioAutenticarService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    System.out.println("Autenticação bem-sucedida: " + isAuthenticated);

    if (isAuthenticated) {
        Long userId = usuarioService.findByEmail(loginRequest.getUsername());
        System.out.println("ID do usuário recuperado: " + userId);

        if (userId != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                System.out.println("Sessão atual invalidada para o usuário: " + userId);
            }

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("userId", userId);

            SessionFilter.addValidSession(newSession.getId());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            response.put("sessionId", newSession.getId());
            System.out.println("Novo Session ID gerado no login: " + newSession.getId());
            return ResponseEntity.ok(response);
        } else {
            System.out.println("Usuário não encontrado");
            return ResponseEntity.status(401).body(Map.of("error", "Usuário não encontrado"));
        }
    } else {
        System.out.println("Credenciais inválidas");
        return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
    }
}
}
