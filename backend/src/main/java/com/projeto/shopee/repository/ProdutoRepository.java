package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByLojaId(Long lojaId);  // Adicionado método para buscar produtos por ID da loja
}
