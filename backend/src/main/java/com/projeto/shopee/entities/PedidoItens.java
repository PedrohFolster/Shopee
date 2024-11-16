package com.projeto.shopee.entities;

import jakarta.persistence.*;

@Entity
public class PedidoItens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeItem;
    private Double valor;
    private Integer quantidade;
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "idPedido", referencedColumnName = "idPedido")
    private Pedido pedido;

    public PedidoItens() {
    }

    public PedidoItens(Long id, String nomeItem, Double valor, Integer quantidade, Double valorTotal, Pedido pedido) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.valor = valor;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.pedido = pedido;
    }

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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
} 