package com.transportadora.user.repository;

import com.transportadora.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndUsername(String email, String username);

    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(CAST(FUNCTION('unaccent', u.name) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', u.username) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :filter) AS string)), '%'))")
    Page<User> findAllByFilter(@Param("filter") String filter, Pageable pageable);

}
