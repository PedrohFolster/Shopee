package com.projeto.shopee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.UsuarioAutenticar;

@Repository
public interface UsuarioAutenticarRepository extends JpaRepository<UsuarioAutenticar, Long> {
    Optional<UsuarioAutenticar> findByLogin(String login);
}
