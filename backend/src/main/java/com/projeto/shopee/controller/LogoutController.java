package com.projeto.shopee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        System.out.println("Método de logout chamado.");
        System.out.println("Logout solicitado para o token: " + token);
        return ResponseEntity.ok().build();
    }
}
