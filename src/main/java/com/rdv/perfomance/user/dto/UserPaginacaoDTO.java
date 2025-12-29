package com.rdv.perfomance.user.dto;

import java.util.List;

public record UserPaginacaoDTO(List<UserDTO> usuarios, long totalElementos, int totalPaginas) {
}