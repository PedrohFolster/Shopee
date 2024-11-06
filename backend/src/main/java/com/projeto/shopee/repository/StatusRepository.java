package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.projeto.shopee.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByNomeStatus(String nomeStatus);
}
