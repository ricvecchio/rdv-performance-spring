package com.transportadora.management.dto;

import java.util.List;

public class GastoMensalDTO {
    private Integer anoTotal;
    private Integer mesTotal;
    private Double valorTotalMes;
    private List<ClienteGastoDTO> clientes;

    public GastoMensalDTO(Integer anoTotal, Integer mesTotal, Double valorTotalMes, List<ClienteGastoDTO> clientes) {
        this.anoTotal = anoTotal;
        this.mesTotal = mesTotal;
        this.valorTotalMes = valorTotalMes;
        this.clientes = clientes;
    }

    public Integer getAnoTotal() {
        return anoTotal;
    }

    public Integer getMesTotal() {
        return mesTotal;
    }

    public Double getValorTotalMes() {
        return valorTotalMes;
    }

    public List<ClienteGastoDTO> getClientes() {
        return clientes;
    }
}


