package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projeto.shopee.entities.CategoriaLoja;

@Repository
public interface CategoriaLojaRepository extends JpaRepository<CategoriaLoja, Long> {
}
