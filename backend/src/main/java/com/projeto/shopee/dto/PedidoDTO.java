package com.projeto.shopee.dto;

import java.util.List;
import java.time.LocalDate;

public class PedidoDTO {
    private Long idPedido;
    private Long idUsuario;
    private Double valorTotal;
    private List<PedidoItensDTO> pedidoItens;
    private LocalDate dataPedido;
    
    
    
    public PedidoDTO() {
    }

    public PedidoDTO(Long idPedido, Long idUsuario, Double valorTotal, List<PedidoItensDTO> pedidoItens, LocalDate dataPedido) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.valorTotal = valorTotal;
        this.pedidoItens = pedidoItens;
        this.dataPedido = dataPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public List<PedidoItensDTO> getPedidoItens() {
        return pedidoItens;
    }
    public void setPedidoItens(List<PedidoItensDTO> pedidoItens) {
        this.pedidoItens = pedidoItens;
    }
    public LocalDate getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    
    
} 