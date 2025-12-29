package com.rdv.perfomance.management.dto;

import java.util.List;

public record PedidoPaginacaoDTO(List<PedidoDTO> pedidos, long totalElementos, int totalPaginas) {
}