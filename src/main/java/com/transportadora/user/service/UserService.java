package com.transportadora.user.service;

import com.transportadora.management.model.Pedido;
import com.transportadora.management.repository.PedidoRepository;
import com.transportadora.user.dto.*;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Cacheable(value = "users")
    public UserPaginacaoDTO list(int page, int pageSize, String filter) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> pageUser = userRepository.findAllByFilter(filter, pageable);

        List<UserDTO> users = pageUser.getContent().stream().map(user ->
                new UserDTO(
                        user.getIdUser(),
                        user.getName(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getPermission()
                )
        ).collect(Collectors.toList());
        return new UserPaginacaoDTO(users, pageUser.getTotalElements(), pageUser.getTotalPages());
    }

    public Optional<User> findByUsername(@PathVariable @NotNull String username) {
        return userRepository.findByUsername(username);
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserDTO update(@NotNull String idUser, @Valid UserDTO userDTO) {
        return userRepository.findById(UUID.fromString(idUser))
                .map(recordFound -> {
                    recordFound.setPermission(userDTO.permission());
                    User updatedUser = userRepository.save(recordFound);
                    return convertToDTO(updatedUser);
                }).orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getIdUser(), user.getUsername(), user.getEmail(), user.getUsername(), user.getPassword(), user.getPermission());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(@NotNull String idUser) {
        userRepository.delete(userRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser)));
    }

    public ResponseEntity<?> login(LoginRequestDTO body) {
        Optional<User> user = userRepository.findByUsername(body.username());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Username não cadastrado."));
        }

        if (!passwordEncoder.matches(body.password(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Senha incorreta."));
        }

        String token = tokenService.generateToken(user.get());
        return ResponseEntity.ok(new ResponseDTO(user.get().getUsername(), token, user.get().getPermission()));

    }

    @CacheEvict(value = "users", allEntries = true)
    public void register(RegisterRequestDTO body) throws Exception {
        Optional<User> user = userRepository.findByUsername(body.username());

        if (user.isPresent()) {
            throw new Exception("Username já cadastrado!");
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setUsername(body.username());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        userRepository.save(newUser);
    }

}

