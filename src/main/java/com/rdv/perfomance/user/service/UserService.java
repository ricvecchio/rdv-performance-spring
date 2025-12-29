package com.rdv.perfomance.user.service;

import com.rdv.perfomance.user.dto.*;
import com.rdv.perfomance.user.entities.AccountStatus;
import com.rdv.perfomance.user.entities.PlanType;
import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.entities.UserType;
import com.rdv.perfomance.user.repository.UserRepository;
import com.rdv.perfomance.user.security.TokenService;
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
                        user.getPhone(),
                        user.getUserType(),
                        user.getFocusArea(),
                        user.getStatus(),
                        user.getPlanType(),
                        user.getCref(),
                        user.getBio(),
                        user.getGymName(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                )
        ).collect(Collectors.toList());
        return new UserPaginacaoDTO(users, pageUser.getTotalElements(), pageUser.getTotalPages());
    }

    public Optional<User> findByUsername(@PathVariable @NotNull String username) {
        // keep for compatibility: try findByEmail
        return userRepository.findByEmail(username);
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserDTO update(@NotNull String idUser, @Valid UserDTO userDTO) {
        return userRepository.findById(UUID.fromString(idUser))
                .map(recordFound -> {
                    // update allowed fields
                    recordFound.setName(userDTO.name());
                    recordFound.setPhone(userDTO.phone());
                    recordFound.setFocusArea(userDTO.focusArea());
                    recordFound.setPlanType(userDTO.planType());
                    recordFound.setCref(userDTO.cref());
                    recordFound.setBio(userDTO.bio());
                    recordFound.setGymName(userDTO.gymName());
                    User updatedUser = userRepository.save(recordFound);
                    return convertToDTO(updatedUser);
                }).orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getIdUser(), user.getName(), user.getEmail(), user.getPhone(), user.getUserType(), user.getFocusArea(), user.getStatus(), user.getPlanType(), user.getCref(), user.getBio(), user.getGymName(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(@NotNull String idUser) {
        // soft delete: set status to CANCELED
        User user = userRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser));
        user.setStatus(AccountStatus.CANCELED);
        userRepository.save(user);
    }

    public ResponseEntity<?> login(LoginRequestDTO body) {
        Optional<User> user = userRepository.findByEmail(body.email);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Email não cadastrado."));
        }

        if (!passwordEncoder.matches(body.password, user.get().getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Senha incorreta."));
        }

        if (user.get().getStatus() != AccountStatus.ACTIVE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Conta não ativa."));
        }

        String token = tokenService.generateToken(user.get());
        var u = user.get();
        return ResponseEntity.ok(new ResponseDTO(u.getIdUser(), u.getName(), u.getEmail(), token, u.getUserType(), u.getPlanType(), u.getStatus()));

    }

    @CacheEvict(value = "users", allEntries = true)
    public void register(RegisterRequestDTO body) throws Exception {
        Optional<User> user = userRepository.findByEmail(body.email);

        if (user.isPresent()) {
            throw new Exception("Email já cadastrado!");
        }

        User newUser = new User();
        newUser.setName(body.name);
        newUser.setEmail(body.email);
        newUser.setPasswordHash(passwordEncoder.encode(body.password));
        newUser.setPhone(body.phone);
        newUser.setUserType(body.userType != null ? body.userType : UserType.STUDENT);
        newUser.setFocusArea(body.focusArea);
        newUser.setPlanType(body.planType != null ? body.planType : PlanType.FREE);
        newUser.setStatus(AccountStatus.ACTIVE);
        newUser.setCref(body.cref);
        newUser.setBio(body.bio);
        newUser.setGymName(body.gymName);

        // validation: if trainer, cref required
        if (newUser.getUserType() == UserType.TRAINER && (newUser.getCref() == null || newUser.getCref().isBlank())) {
            throw new Exception("CREF é obrigatório para TRAINER");
        }

        userRepository.save(newUser);
    }

}
