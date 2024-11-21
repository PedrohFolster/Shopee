package com.projeto.shopee.controller;

import com.projeto.shopee.entities.StatusPedido;
import com.projeto.shopee.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status-pedidos")
public class StatusPedidoController {

    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    @GetMapping
    public List<StatusPedido> getAllStatusPedidos() {
        return statusPedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusPedido> getStatusPedidoById(@PathVariable Long id) {
        Optional<StatusPedido> statusPedido = statusPedidoRepository.findById(id);
        return statusPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StatusPedido createStatusPedido(@RequestBody StatusPedido statusPedido) {
        return statusPedidoRepository.save(statusPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusPedido> updateStatusPedido(@PathVariable Long id, @RequestBody StatusPedido statusPedidoDetails) {
        Optional<StatusPedido> statusPedido = statusPedidoRepository.findById(id);
        if (statusPedido.isPresent()) {
            StatusPedido updatedStatusPedido = statusPedido.get();
            updatedStatusPedido.setNomeStatus(statusPedidoDetails.getNomeStatus());
            return ResponseEntity.ok(statusPedidoRepository.save(updatedStatusPedido));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatusPedido(@PathVariable Long id) {
        if (statusPedidoRepository.existsById(id)) {
            statusPedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}