package com.projeto.shopee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.shopee.entities.Status;
import com.projeto.shopee.repository.StatusRepository;

@SpringBootApplication
public class ShopeeApplication implements CommandLineRunner {

    @Autowired
    private StatusRepository statusRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShopeeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (!statusRepository.existsById(1L)) {
            Status statusAtivo = new Status();
            statusAtivo.setId(1L);
            statusAtivo.setNomeStatus("Ativo");
            statusRepository.save(statusAtivo);
            System.out.println("Status 'Ativo' criado com ID 1.");
        } else {
            System.out.println("Status com ID 1 já existe.");
        }

        if (!statusRepository.existsById(2L)) {
            Status statusInativo = new Status();
            statusInativo.setId(2L);
            statusInativo.setNomeStatus("Inativo");
            statusRepository.save(statusInativo);
            System.out.println("Status 'Inativo' criado com ID 2.");
        } else {
            System.out.println("Status com ID 2 já existe.");
        }
    }
}
