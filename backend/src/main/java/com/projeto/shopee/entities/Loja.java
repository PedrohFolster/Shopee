package com.projeto.shopee.entities;

import jakarta.persistence.*;

@Entity
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne  
    @JoinColumn(name = "categoria_loja_id")
    private CategoriaLoja categoriaLoja; 

    @OneToOne  
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;  

    public Loja() {}

    public Loja(Long id, String nome, CategoriaLoja categoriaLoja, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.categoriaLoja = categoriaLoja;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaLoja getCategoriaLoja() {
        return categoriaLoja;
    }

    public void setCategoriaLoja(CategoriaLoja categoriaLoja) {
        this.categoriaLoja = categoriaLoja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
