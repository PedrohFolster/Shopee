package com.projeto.shopee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

    boolean existsByUsuarioId(Long usuarioId);

    Optional<Loja> findByUsuarioId(Long usuarioId); // Retorna Optional

    Optional<Loja> findByIdAndUsuarioId(Long id, Long usuarioId);
}