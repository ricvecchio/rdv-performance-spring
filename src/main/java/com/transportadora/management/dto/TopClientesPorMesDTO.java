package com.transportadora.management.dto;

public class TopClientesPorMesDTO {

    private Long idCliente;
    private Double precoTotal;
    private Integer mesTotal;
    private Integer anoTotal;

    public TopClientesPorMesDTO(Long idCliente, Double precoTotal, Integer mesTotal, Integer anoTotal) {
        this.idCliente = idCliente;
        this.precoTotal = precoTotal;
        this.mesTotal = mesTotal;
        this.anoTotal = anoTotal;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public Integer getMesTotal() {
        return mesTotal;
    }

    public Integer getAnoTotal() {
        return anoTotal;
    }
}
