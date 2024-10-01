package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
