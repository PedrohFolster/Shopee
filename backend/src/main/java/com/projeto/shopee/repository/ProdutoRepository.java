package com.projeto.shopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByLojaId(Long lojaId);
    List<Produto> findByStatusNomeStatus(String nomeStatus);
    List<Produto> findByCategoriaProdutoId(Long categoriaProdutoId);
}
