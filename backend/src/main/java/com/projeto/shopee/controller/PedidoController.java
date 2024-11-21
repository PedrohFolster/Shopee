package com.projeto.shopee.controller;

import com.projeto.shopee.service.PedidoService;
import com.projeto.shopee.security.JwtService;
import com.projeto.shopee.dto.PedidoDTO;
import com.projeto.shopee.util.PedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.shopee.entities.Pedido;
import com.projeto.shopee.dto.PedidoItensDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PedidoMapper pedidoMapper;

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

    @GetMapping
    public ResponseEntity<?> getPedidosByUserId(@RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtService.getUserIdFromToken(token.substring(7));
            List<Pedido> pedidos = pedidoService.getPedidosByUserId(userId);
            List<PedidoDTO> pedidosDTO = pedidos.stream()
                                                .map(pedidoMapper::toDTO)
                                                .collect(Collectors.toList());
            return ResponseEntity.ok(pedidosDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/itens-pedidos/{lojaId}")
    public ResponseEntity<?> getItensVendidosPorLoja(@PathVariable Long lojaId) {
        try {
            List<PedidoItensDTO> itensVendidos = pedidoService.getItensVendidosPorLoja(lojaId).stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(itensVendidos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 