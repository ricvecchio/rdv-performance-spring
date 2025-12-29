package com.transportadora.management.repository;

import com.transportadora.management.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findAllByOrderByIdPedidoDesc(Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido BETWEEN :dataInicial AND :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataBetweenAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido >= :dataInicial " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataAfterAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido <= :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataBeforeAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido BETWEEN :dataInicial AND :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataBetweenAndStatus(
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido >= :dataInicial " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataAfterAndStatus(
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido <= :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataBeforeAndStatus(
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query(value = "SELECT p FROM Pedido p WHERE p.status = :statusFiltro ORDER BY p.idPedido DESC",
            countQuery = "SELECT COUNT(p) FROM Pedido p WHERE p.status = :statusFiltro")
    Page<Pedido> findByStatus(@Param("statusFiltro") String statusFiltro, Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE p.idCliente = :idCliente ORDER BY p.dataAtualizacaoPedido DESC")
    List<Pedido> findTopByIdClienteOrderByDataAtualizacaoPedidoDesc(
            @Param("idCliente") Long idCliente,
            Pageable pageable);

    @Query(value =
            "SELECT p.id_cliente, p.preco_final, p.data_atualizacao_pedido " +
                    "FROM pedidos p " +
                    "WHERE EXTRACT(YEAR FROM p.data_atualizacao_pedido) = ?1 ",
            nativeQuery = true)
    List<Object[]> findPedidosParaProcessamento(int ano);

}
