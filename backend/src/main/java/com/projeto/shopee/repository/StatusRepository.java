package com.projeto.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.shopee.entities.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
