package com.transportadora.management.controller;

import com.transportadora.management.dto.PedidoDTO;
import com.transportadora.management.dto.PedidoPaginacaoDTO;
import com.transportadora.management.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public PedidoPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100000) int pageSize,
            @RequestParam(required = false) String clienteFiltro,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) String statusFiltro) {
        return pedidoService.list(page, pageSize, clienteFiltro, dataInicial, dataFinal, statusFiltro);
    }

    @GetMapping("/{idPedido}")
    public PedidoDTO findById(@PathVariable @NotNull @Positive Long idPedido) {
        return pedidoService.findById(idPedido);
    }

    @GetMapping("/ultimos")
    public List<PedidoDTO> findLastPedidosByCliente(
            @RequestParam @NotNull @Positive Long idCliente,
            @RequestParam(defaultValue = "3") @Positive int limite) {
        return pedidoService.findLastPedidosByCliente(idCliente, limite);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PedidoDTO save(@RequestBody @Valid @NotNull PedidoDTO pedido) {
        return pedidoService.create(pedido);
    }

    @PutMapping("/{idPedido}")
    public PedidoDTO update(@PathVariable @NotNull @Positive Long idPedido, @RequestBody @Valid PedidoDTO pedido) {
        return pedidoService.update(idPedido, pedido);
    }

    @DeleteMapping("/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long idPedido) {
        pedidoService.cancel(idPedido);
    }

}
