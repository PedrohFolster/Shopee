package com.projeto.shopee.repository;

import com.projeto.shopee.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
} 