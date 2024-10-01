package com.projeto.shopee.dto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private String imagem;
    private int estoque;
    private Long statusId;
    private Long categoriaProdutoId;
    private Long lojaId;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String descricao, double preco, String imagem, int estoque, Long statusId, Long categoriaProdutoId, Long lojaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
        this.estoque = estoque;
        this.statusId = statusId;
        this.categoriaProdutoId = categoriaProdutoId;
        this.lojaId = lojaId;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getCategoriaProdutoId() {
        return categoriaProdutoId;
    }

    public void setCategoriaProdutoId(Long categoriaProdutoId) {
        this.categoriaProdutoId = categoriaProdutoId;
    }

    public Long getLojaId() {
        return lojaId;
    }

    public void setLojaId(Long lojaId) {
        this.lojaId = lojaId;
    }
}
