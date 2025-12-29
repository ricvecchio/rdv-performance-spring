package com.transportadora.management.dto.mapper;

import com.transportadora.management.dto.PedidoDTO;
import com.transportadora.management.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getNome(),
                pedido.getCpfCnpj(),
                pedido.getRazaoSocial(),
                pedido.getIdCliente(),
                pedido.getTelefone(),
                pedido.getCelular(),
                pedido.getEmail(),
                pedido.getContatosAdicionais(),
                pedido.getCep(),
                pedido.getLogradouro(),
                pedido.getNumero(),
                pedido.getComplemento(),
                pedido.getBairro(),
                pedido.getCidade(),
                pedido.getEstado(),
                pedido.getTipoPgto(),
                pedido.getInfoPagamento(),
                pedido.getCepEntrega(),
                pedido.getLogradouroEntrega(),
                pedido.getNumeroEntrega(),
                pedido.getComplementoEntrega(),
                pedido.getBairroEntrega(),
                pedido.getCidadeEntrega(),
                pedido.getEstadoEntrega(),
                pedido.getSfobras(),
                pedido.getCno(),
                pedido.getIe(),
                pedido.getMangueira(),
                pedido.getValorAjudante(),
                pedido.getValorAdicional(),
                pedido.getVolume(),
                pedido.getPrecoCx5(),
                pedido.getPrecoCx10(),
                pedido.getPrecoCx15(),
                pedido.getPrecoLv5(),
                pedido.getPrecoLv10(),
                pedido.getPrecoLv15(),
                pedido.getAjudante(),
                pedido.getAdicional(),
                pedido.getPrecoFinal(),
                pedido.getObservacao(),
                pedido.getStatus(),
                pedido.getDataAtualizacaoPedido(),
                pedido.getImagemPedido());
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        if (pedidoDTO.idPedido() != null) {
            pedido.setIdPedido(pedidoDTO.idPedido());
        }
        pedido.setNome(pedidoDTO.nome());
        pedido.setCpfCnpj(pedidoDTO.cpfCnpj());
        pedido.setRazaoSocial(pedidoDTO.razaoSocial());
        pedido.setIdCliente(pedidoDTO.idCliente());
        pedido.setTelefone(pedidoDTO.telefone());
        pedido.setCelular(pedidoDTO.celular());
        pedido.setEmail(pedidoDTO.email());
        pedido.setContatosAdicionais(pedidoDTO.contatosAdicionais());
        pedido.setCep(pedidoDTO.cep());
        pedido.setLogradouro(pedidoDTO.logradouro());
        pedido.setNumero(pedidoDTO.numero());
        pedido.setComplemento(pedidoDTO.complemento());
        pedido.setBairro(pedidoDTO.bairro());
        pedido.setCidade(pedidoDTO.cidade());
        pedido.setEstado(pedidoDTO.estado());
        pedido.setTipoPgto(pedidoDTO.tipoPgto());
        pedido.setInfoPagamento(pedidoDTO.infoPagamento());
        pedido.setCepEntrega(pedidoDTO.cepEntrega());
        pedido.setLogradouroEntrega(pedidoDTO.logradouroEntrega());
        pedido.setNumeroEntrega(pedidoDTO.numeroEntrega());
        pedido.setComplementoEntrega(pedidoDTO.complementoEntrega());
        pedido.setBairroEntrega(pedidoDTO.bairroEntrega());
        pedido.setCidadeEntrega(pedidoDTO.cidadeEntrega());
        pedido.setEstadoEntrega(pedidoDTO.estadoEntrega());
        pedido.setSfobras(pedidoDTO.sfobras());
        pedido.setCno(pedidoDTO.cno());
        pedido.setIe(pedidoDTO.ie());
        pedido.setMangueira(pedidoDTO.mangueira());
        pedido.setValorAjudante(pedidoDTO.valorAjudante());
        pedido.setValorAdicional(pedidoDTO.valorAdicional());
        pedido.setVolume(pedidoDTO.volume());
        pedido.setPrecoCx5(pedidoDTO.precoCx5());
        pedido.setPrecoCx10(pedidoDTO.precoCx10());
        pedido.setPrecoCx15(pedidoDTO.precoCx15());
        pedido.setPrecoLv5(pedidoDTO.precoLv5());
        pedido.setPrecoLv10(pedidoDTO.precoLv10());
        pedido.setPrecoLv15(pedidoDTO.precoLv15());
        pedido.setAjudante(pedidoDTO.ajudante());
        pedido.setAdicional(pedidoDTO.adicional());
        pedido.setPrecoFinal(pedidoDTO.precoFinal());
        pedido.setObservacao(pedidoDTO.observacao());
        pedido.setStatus(pedidoDTO.status());
        pedido.setDataAtualizacaoPedido(pedidoDTO.dataAtualizacaoPedido());
        pedido.setImagemPedido(pedidoDTO.imagemPedido());
        return pedido;
    }
};