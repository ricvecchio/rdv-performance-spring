package com.transportadora.management.dto;

import java.util.List;

public record PedidoPaginacaoDTO(List<PedidoDTO> pedidos, long totalElementos, int totalPaginas) {
}