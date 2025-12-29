package com.transportadora.management.repository;

import com.transportadora.management.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfCnpj(String cpfCnpj);

    @Query("SELECT c FROM Cliente c WHERE " +
            "CAST(c.idCliente AS string) LIKE CONCAT('%', :filter, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', c.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', c.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', c.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(c.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:filter, '.', ''), '-', ''), '/', ''), '%'))")
    List<Cliente> clientesPorTrecho(@Param("filter") String filter);

    Page<Cliente> findAllByOrderByIdClienteDesc(Pageable pageable);

    @Query("SELECT c FROM Cliente c WHERE " +
            "CAST(c.idCliente AS string) LIKE CONCAT('%', :filter, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', c.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', c.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', c.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(c.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:filter, '.', ''), '-', ''), '/', ''), '%'))")
    Page<Cliente> findAllByFilter(@Param("filter") String filter, Pageable pageable);

}
