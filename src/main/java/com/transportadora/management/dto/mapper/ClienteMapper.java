package com.transportadora.management.dto.mapper;

import com.transportadora.management.dto.ClienteDTO;
import com.transportadora.management.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getCpfCnpj(),
                cliente.getRazaoSocial(),
                cliente.getTelefone(),
                cliente.getCelular(),
                cliente.getEmail(),
                cliente.getContatosAdicionais(),
                cliente.getCep(),
                cliente.getLogradouro(),
                cliente.getNumero(),
                cliente.getComplemento(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getTipoPgto(),
                cliente.getInfoPagamento(),
                cliente.getCepEntrega(),
                cliente.getLogradouroEntrega(),
                cliente.getNumeroEntrega(),
                cliente.getComplementoEntrega(),
                cliente.getBairroEntrega(),
                cliente.getCidadeEntrega(),
                cliente.getEstadoEntrega(),
                cliente.getSfobras(),
                cliente.getCno(),
                cliente.getIe(),
                cliente.getMangueira(),
                cliente.getValorAjudante(),
                cliente.getValorAdicional(),
                cliente.getPrecoCx5(),
                cliente.getPrecoCx10(),
                cliente.getPrecoCx15(),
                cliente.getPrecoLv5(),
                cliente.getPrecoLv10(),
                cliente.getPrecoLv15(),
                cliente.getObservacao(),
                cliente.getDataAtualizacaoCliente());
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {

        if (clienteDTO == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        if (clienteDTO.idCliente() != null) {
            cliente.setIdCliente(clienteDTO.idCliente());
        }
        cliente.setNome(clienteDTO.nome());
        cliente.setCpfCnpj(clienteDTO.cpfCnpj());
        cliente.setRazaoSocial(clienteDTO.razaoSocial());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setCelular(clienteDTO.celular());
        cliente.setEmail(clienteDTO.email());
        cliente.setContatosAdicionais(clienteDTO.contatosAdicionais());
        cliente.setCep(clienteDTO.cep());
        cliente.setLogradouro(clienteDTO.logradouro());
        cliente.setNumero(clienteDTO.numero());
        cliente.setComplemento(clienteDTO.complemento());
        cliente.setBairro(clienteDTO.bairro());
        cliente.setCidade(clienteDTO.cidade());
        cliente.setEstado(clienteDTO.estado());
        cliente.setTipoPgto(clienteDTO.tipoPgto());
        cliente.setInfoPagamento(clienteDTO.infoPagamento());
        cliente.setCepEntrega(clienteDTO.cepEntrega());
        cliente.setLogradouroEntrega(clienteDTO.logradouroEntrega());
        cliente.setNumeroEntrega(clienteDTO.numeroEntrega());
        cliente.setComplementoEntrega(clienteDTO.complementoEntrega());
        cliente.setBairroEntrega(clienteDTO.bairroEntrega());
        cliente.setCidadeEntrega(clienteDTO.cidadeEntrega());
        cliente.setEstadoEntrega(clienteDTO.estadoEntrega());
        cliente.setSfobras(clienteDTO.sfobras());
        cliente.setCno(clienteDTO.cno());
        cliente.setIe(clienteDTO.ie());
        cliente.setMangueira(clienteDTO.mangueira());
        cliente.setValorAjudante(clienteDTO.valorAjudante());
        cliente.setValorAdicional(clienteDTO.valorAdicional());
        cliente.setPrecoCx5(clienteDTO.precoCx5());
        cliente.setPrecoCx10(clienteDTO.precoCx10());
        cliente.setPrecoCx15(clienteDTO.precoCx15());
        cliente.setPrecoLv5(clienteDTO.precoLv5());
        cliente.setPrecoLv10(clienteDTO.precoLv10());
        cliente.setPrecoLv15(clienteDTO.precoLv15());
        cliente.setObservacao(clienteDTO.observacao());
        cliente.setDataAtualizacaoCliente(clienteDTO.dataAtualizacaoCliente());

        return cliente;
    }

}
