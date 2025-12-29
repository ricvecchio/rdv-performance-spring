package com.transportadora.user.service;

import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "users")
    public void sendRecoveryEmail(String email, String username) {
        User user = userRepository.findByEmailAndUsername(email, username)
                .orElseThrow(() -> new RuntimeException("Usuário ou e-mail não encontrado!"));

        String token = tokenService.generateToken(user);
        String recoveryLink = "https://saotomecatimesaotomecatime.com/home/recuperar-senha?token=" + token;

        emailService.sendEmail(user.getEmail(), "Recuperação de Senha",
                "Clique no link para redefinir sua senha:\nLink: " + recoveryLink);
    }

    @Cacheable(value = "users")
    public void resetPassword(String token, String newPassword) {
        String username = tokenService.validateToken(token);
        if (username == null) {
            throw new RuntimeException("Token inválido ou expirado!");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

