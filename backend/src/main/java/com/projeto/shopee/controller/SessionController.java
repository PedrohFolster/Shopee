package com.projeto.shopee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.security.SessionFilter;

@RestController
@RequestMapping("/validate-session")
public class SessionController {

    private final SessionFilter sessionFilter;

    public SessionController(SessionFilter sessionFilter) {
        this.sessionFilter = sessionFilter;
    }

    @GetMapping
    public ResponseEntity<Void> validateSession(@RequestHeader("Authorization") String token) {
        System.out.println("Token JWT recebido: " + token);

        if (sessionFilter.isValidSession(token.replace("Bearer ", ""))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}