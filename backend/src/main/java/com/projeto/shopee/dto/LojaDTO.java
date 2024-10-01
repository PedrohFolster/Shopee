package com.projeto.shopee.dto;

public class LojaDTO {

    private Long id;
    private String nome;
    private Long categoriaLojaId;  // Adicionado para armazenar o ID de CategoriaLoja
    private Long usuarioId;         // Adicionado para armazenar o ID de Usuario

    // Construtores
    public LojaDTO() {}

    public LojaDTO(Long id, String nome, Long categoriaLojaId, Long usuarioId) {
        this.id = id;
        this.nome = nome;
        this.categoriaLojaId = categoriaLojaId;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
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

    public Long getCategoriaLojaId() {
        return categoriaLojaId;
    }

    public void setCategoriaLojaId(Long categoriaLojaId) {
        this.categoriaLojaId = categoriaLojaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
