package com.rdv.perfomance.management.dto;

import java.util.List;

public class DashboardDTO {
    private List<GastoMensalDTO> dados;

    public DashboardDTO(List<GastoMensalDTO> dados) {
        this.dados = dados;
    }

    public List<GastoMensalDTO> getDados() {
        return dados;
    }

    public void setDados(List<GastoMensalDTO> dados) {
        this.dados = dados;
    }
}
