package com.projeto.shopee.repository;

import com.projeto.shopee.entities.PedidoItens;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoItensRepository extends JpaRepository<PedidoItens, Long> {
    List<PedidoItens> findByLojaId(Long lojaId);
} 