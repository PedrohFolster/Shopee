package com.projeto.shopee.dto;

public class PedidoItensDTO {
    private Long id;
    private String nomeItem;
    private Double valor;
    private Integer quantidade;
    private Double valorTotal;
    private Long produtoId;
    private Long lojaId;
    private String status;
    private Long idPedido;

    public PedidoItensDTO() {
    }

    public PedidoItensDTO(Long id, String nomeItem, Double valor, Integer quantidade, Double valorTotal, Long idPedido) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.valor = valor;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.idPedido = idPedido;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getLojaId() {
        return lojaId;
    }

    public void setLojaId(Long lojaId) {
        this.lojaId = lojaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}