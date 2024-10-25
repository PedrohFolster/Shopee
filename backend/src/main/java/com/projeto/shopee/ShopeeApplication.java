package com.projeto.shopee;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.shopee.entities.Endereco;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.repository.StatusRepository;
import com.projeto.shopee.repository.UsuarioRepository;

@SpringBootApplication
public class ShopeeApplication implements CommandLineRunner {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShopeeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Criação dos Status
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

        // Criação do Usuário João Silva
        if (!usuarioRepository.existsByEmail("1@gmail.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("João Silva");
            usuario.setCpf("07894276995");
            usuario.setEmail("1@gmail.com");
            usuario.setTelefone("48984868321");
            usuario.setDataNascimento(LocalDate.of(2000, 5, 15));

            Endereco endereco = new Endereco();
            endereco.setCep("88064000");
            endereco.setRua("Rua Exemplo");
            endereco.setNumero("100");
            endereco.setCidade("São Paulo");
            endereco.setEstado("SP");
            endereco.setPais("Brasil");
            endereco.setComplemento("Apt 101");
            usuario.setEndereco(endereco);

            UsuarioAutenticar usuarioAutenticar = new UsuarioAutenticar();
            usuarioAutenticar.setUsername("1@gmail.com");
            usuarioAutenticar.setPasswordHash("Caruso123!");
            usuario.setUsuarioAutenticar(usuarioAutenticar);

            usuarioRepository.save(usuario);
            System.out.println("Usuário 'João Silva' criado.");
        } else {
            System.out.println("Usuário com email 'joao.silva@example.com' já existe.");
        }

        // Criação do Usuário Maria Oliveira
        if (!usuarioRepository.existsByEmail("2@gmail.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("Maria Oliveira");
            usuario.setCpf("12345678901");
            usuario.setEmail("2@gmail.com");
            usuario.setTelefone("48984868322");
            usuario.setDataNascimento(LocalDate.of(1995, 8, 20));

            Endereco endereco = new Endereco();
            endereco.setCep("88064001");
            endereco.setRua("Rua Nova");
            endereco.setNumero("200");
            endereco.setCidade("Rio de Janeiro");
            endereco.setEstado("RJ");
            endereco.setPais("Brasil");
            endereco.setComplemento("Casa 2");
            usuario.setEndereco(endereco);

            UsuarioAutenticar usuarioAutenticar = new UsuarioAutenticar();
            usuarioAutenticar.setUsername("2@gmail.com");
            usuarioAutenticar.setPasswordHash("Caruso123!");
            usuario.setUsuarioAutenticar(usuarioAutenticar);

            usuarioRepository.save(usuario);
            System.out.println("Usuário 'Maria Oliveira' criado.");
        } else {
            System.out.println("Usuário com email 'maria.oliveira@example.com' já existe.");
        }
    }
}
