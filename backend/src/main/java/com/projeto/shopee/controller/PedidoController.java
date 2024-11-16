package com.projeto.shopee.controller;

import com.projeto.shopee.service.PedidoService;
import com.projeto.shopee.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/finalizar-compra")
    public ResponseEntity<?> finalizarCompra(@RequestBody List<Map<String, Object>> produtos,
                                             @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtService.getUserIdFromToken(token.substring(7));
            pedidoService.finalizarCompra(produtos, userId);
            return ResponseEntity.ok("Compra finalizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 