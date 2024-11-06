package com.projeto.shopee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController

public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
            System.out.println("Sessão encerrada.");
        }
        return ResponseEntity.ok().build();
    }
}
