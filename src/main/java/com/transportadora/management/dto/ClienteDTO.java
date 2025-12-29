package com.transportadora.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record ClienteDTO(
        @JsonProperty("idCliente") Long idCliente,
        @NotBlank @NotNull @Length(min = 1, max = 100) String nome,
        String cpfCnpj,
        String razaoSocial,
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
        String precoCx5,
        String precoCx10,
        String precoCx15,
        String precoLv5,
        String precoLv10,
        String precoLv15,
        String observacao,
        LocalDateTime dataAtualizacaoCliente) {
}





