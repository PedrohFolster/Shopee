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

@GetMapping
public ResponseEntity<Void> validateSession(@RequestHeader("session-id") String sessionId) {
    System.out.println("Session ID recebido: " + sessionId);

    if (SessionFilter.isValidSession(sessionId)) {
        return ResponseEntity.ok().build();
    } else {
        return ResponseEntity.status(401).build();
    }
}
}