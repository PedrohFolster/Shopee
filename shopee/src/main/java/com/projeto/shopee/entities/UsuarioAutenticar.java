package com.projeto.shopee.entities;

import java.security.NoSuchAlgorithmException;

import com.projeto.shopee.util.Hashing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsuarioAutenticar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwordHash;

    public UsuarioAutenticar(Long id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }
    
    public UsuarioAutenticar() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) throws NoSuchAlgorithmException {
        this.passwordHash = Hashing.hash(passwordHash);
    }

    
}
