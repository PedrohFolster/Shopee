package com.projeto.shopee.dto;

public class StatusPedidoDTO {

    private Long id;
    private String nomeStatus;

    public StatusPedidoDTO() {
    }

    public StatusPedidoDTO(Long id, String nomeStatus) {
        this.id = id;
        this.nomeStatus = nomeStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeStatus() {
        return nomeStatus;
    }

    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
    }
}