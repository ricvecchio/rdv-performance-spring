package com.rdv.perfomance.user.service;

import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.repository.UserRepository;
import com.rdv.perfomance.user.security.TokenService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordRecoveryService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordRecoveryService(UserRepository userRepository, TokenService tokenService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "users")
    public void sendRecoveryEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário ou e-mail não encontrado!"));

        String token = tokenService.generateToken(user);
        String recoveryLink = "https://saotomecatimesaotomecatime.com/home/recuperar-senha?token=" + token;

        emailService.sendEmail(user.getEmail(), "Recuperação de Senha",
                "Clique no link para redefinir sua senha:\nLink: " + recoveryLink);
    }

    @Cacheable(value = "users")
    public void resetPassword(String token, String newPassword) {
        String subject = tokenService.validateToken(token);
        if (subject == null) {
            throw new RuntimeException("Token inválido ou expirado!");
        }

        UUID id = UUID.fromString(subject);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
