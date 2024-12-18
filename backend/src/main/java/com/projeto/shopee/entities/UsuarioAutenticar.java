package com.projeto.shopee.entities;

import java.security.NoSuchAlgorithmException;

import com.projeto.shopee.util.Hashing;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UsuarioAutenticar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String perfil;

    public UsuarioAutenticar(Long id, String login, String password, String perfil) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.perfil = perfil;
    }

    public UsuarioAutenticar(UsuarioAutenticarDTO dto) throws NoSuchAlgorithmException {
        this.id = dto.getId();
        this.login = dto.getLogin();
        this.password = Hashing.hash(dto.getPassword());
        this.perfil = dto.getPerfil();
    }

    public UsuarioAutenticar() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = Hashing.hash(password);
    }
    public String getPerfil() {
        return perfil;
    }
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    
}
