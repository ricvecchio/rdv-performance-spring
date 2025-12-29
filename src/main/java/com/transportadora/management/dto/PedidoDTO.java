package com.transportadora.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record PedidoDTO(
        @JsonProperty("idPedido") Long idPedido,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nome,
        String cpfCnpj,
        String razaoSocial,
        Long idCliente,
        String telefone,
        String celular,
        String email,
        String contatosAdicionais,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String tipoPgto,
        String infoPagamento,
        String cepEntrega,
        String logradouroEntrega,
        String numeroEntrega,
        String complementoEntrega,
        String bairroEntrega,
        String cidadeEntrega,
        String estadoEntrega,
        String sfobras,
        String cno,
        String ie,
        String mangueira,
        String valorAjudante,
        String valorAdicional,
        String volume,
        String precoCx5,
        String precoCx10,
        String precoCx15,
        String precoLv5,
        String precoLv10,
        String precoLv15,
        String ajudante,
        String adicional,
        String precoFinal,
        String observacao,
        String status,
        LocalDateTime dataAtualizacaoPedido,
        String imagemPedido) {
}