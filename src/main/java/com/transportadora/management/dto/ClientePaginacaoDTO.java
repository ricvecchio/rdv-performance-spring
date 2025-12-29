package com.transportadora.management.dto;

import java.util.List;

public record ClientePaginacaoDTO(List<ClienteDTO> clientes, long totalElementos, int totalPaginas) {
}