package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteGastoDTO;
import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.dto.GastoMensalDTO;
import com.transportadora.management.repository.PedidoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    public DashboardService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Cacheable(value = "Pedidos")
    public DashboardDTO dashboard(int page, int pageSize, String filter, int ano) {

        List<Object[]> pedidos = pedidoRepository.findPedidosParaProcessamento(ano);

        Map<String, Map<Long, Double>> somaPorClientePorMes = new HashMap<>();
        Map<String, Double> somaTotalPorMes = new HashMap<>();

        for (Object[] row : pedidos) {
            Long idCliente = ((Number) row[0]).longValue();
            String precoFinalStr = (String) row[1];
            Timestamp ts = (Timestamp) row[2];
            LocalDateTime data = ts.toLocalDateTime();

            int mes = data.getMonthValue();
            int anoData = data.getYear();
            String chave = anoData + "-" + mes;

            double valor = parsePreco(precoFinalStr);

            somaPorClientePorMes
                    .computeIfAbsent(chave, k -> new HashMap<>())
                    .merge(idCliente, valor, Double::sum);

            somaTotalPorMes.merge(chave, valor, Double::sum);
        }

        List<GastoMensalDTO> resposta = new ArrayList<>();

        for (String chave : somaTotalPorMes.keySet()) {
            Double totalMes = somaTotalPorMes.get(chave);
            String[] partes = chave.split("-");
            Integer anoResp = Integer.parseInt(partes[0]);
            Integer mes = Integer.parseInt(partes[1]);

            Map<Long, Double> clientesMap = somaPorClientePorMes.getOrDefault(chave, Collections.emptyMap());

            List<ClienteGastoDTO> top5Clientes = clientesMap.entrySet()
                    .stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .limit(5)
                    .map(e -> new ClienteGastoDTO(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            resposta.add(new GastoMensalDTO(anoResp, mes, totalMes, top5Clientes));
        }

        resposta.sort(Comparator.comparing(GastoMensalDTO::getAnoTotal)
                .thenComparing(GastoMensalDTO::getMesTotal));

        return new DashboardDTO(resposta);
    }

    private double parsePreco(String preco) {
        if (preco == null) return 0.0;
        String cleaned = preco
                .replaceAll("[\\s\\u00A0]", "")
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".");
        if (cleaned.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}