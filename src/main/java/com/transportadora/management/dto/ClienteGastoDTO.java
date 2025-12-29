package com.transportadora.management.dto;

public class ClienteGastoDTO {
    private Long idCliente;
    private Double precoTotal;

    public ClienteGastoDTO(Long idCliente, Double precoTotal) {
        this.idCliente = idCliente;
        this.precoTotal = precoTotal;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }
}

