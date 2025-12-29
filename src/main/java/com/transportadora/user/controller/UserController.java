package com.transportadora.user.controller;

import com.transportadora.user.dto.*;
import com.transportadora.user.entities.User;
import com.transportadora.user.service.PasswordRecoveryService;
import com.transportadora.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatimesaotomecatime.com")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordRecoveryService passwordRecoveryService;

    @GetMapping("/list")
    public UserPaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        return userService.list(page, pageSize, filter);
    }

    @GetMapping("/{username}")
    public Optional<User> findByUsername(@PathVariable @NotNull String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/{idUser}")
    public UserDTO update(@PathVariable @NotNull String idUser, @RequestBody @Valid UserDTO user) {
        return userService.update(idUser, user);
    }

    @DeleteMapping("/{idUser}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull String idUser) {
        userService.delete(idUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        return userService.login(body);
    }

    @PostMapping("/recoverPassword")
    public ResponseEntity<Map<String, String>> recoverPassword(@RequestBody RecoveryPasswordDTO body) {
        passwordRecoveryService.sendRecoveryEmail(body.email(), body.username());
        return ResponseEntity.ok(Collections.singletonMap("message", "E-mail de recuperação enviado!"));

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordDTO body) {
        passwordRecoveryService.resetPassword(body.token(), body.newPassword());
        return ResponseEntity.ok(Collections.singletonMap("message", "Senha redefinida com sucesso!"));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        try {
            userService.register(body);
            return ResponseEntity.ok(Collections.singletonMap("message", "Usuário cadastrado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Username já cadastrado!"));
        }
    }

}


