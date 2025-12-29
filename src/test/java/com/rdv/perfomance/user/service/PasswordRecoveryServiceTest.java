package com.rdv.perfomance.user.service;

import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.repository.UserRepository;
import com.rdv.perfomance.user.security.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordRecoveryServiceTest {

    @Mock
    private UserRepository userRepository;

    // do not mock EmailService (ByteBuddy issue on Java 25). Use a lightweight fake below.

    @Mock
    private PasswordEncoder passwordEncoder;

    private PasswordRecoveryService passwordRecoveryService;

    static class FakeTokenService extends TokenService {
        @Override
        public String validateToken(String token) {
            return token == null ? null : token;
        }

        @Override
        public String generateToken(User user) { return "token-" + (user.getIdUser() != null ? user.getIdUser().toString() : ""); }
    }

    static class FakeEmailService {
        public String sendEmail(String email, String subject, String body) {
            // in tests, just return success string
            return "E-mail enviado!";
        }
    }

    @Test
    void resetPassword_validToken_updatesPasswordHash() {
        UUID id = UUID.randomUUID();
        String token = id.toString();
        String newPassword = "newpass123";

        User user = new User();
        user.setIdUser(id);
        user.setEmail("x@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("hashed-new");

        TokenService fakeToken = new FakeTokenService();
        FakeEmailService fakeEmail = new FakeEmailService();
        // construct PasswordRecoveryService with fakes
        passwordRecoveryService = new PasswordRecoveryService(userRepository, fakeToken, (EmailService) null, passwordEncoder);

        // We won't use EmailService in resetPassword path; only ensure reset works
        passwordRecoveryService.resetPassword(token, newPassword);

        assertEquals("hashed-new", user.getPasswordHash());
        verify(userRepository, times(1)).save(user);
    }
}
