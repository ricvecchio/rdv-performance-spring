package com.rdv.perfomance.user.service;

import com.rdv.perfomance.user.dto.LoginRequestDTO;
import com.rdv.perfomance.user.dto.RegisterRequestDTO;
import com.rdv.perfomance.user.dto.ResponseDTO;
import com.rdv.perfomance.user.entities.AccountStatus;
import com.rdv.perfomance.user.entities.PlanType;
import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.entities.UserType;
import com.rdv.perfomance.user.repository.UserRepository;
import com.rdv.perfomance.user.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    static class FakeTokenService extends TokenService {
        @Override
        public String generateToken(User user) {
            if (user == null || user.getIdUser() == null) return "";
            return "token-" + user.getIdUser().toString();
        }

        @Override
        public String validateToken(String token) {
            if (token == null || !token.startsWith("token-")) return null;
            return token.substring("token-".length());
        }
    }

    @BeforeEach
    void setUp() {
        TokenService fake = new FakeTokenService();
        userService = new UserService(userRepository, passwordEncoder, fake);
    }

    @Test
    void register_success_creates_user_with_hashed_password() throws Exception {
        RegisterRequestDTO body = new RegisterRequestDTO();
        body.name = "Test User";
        body.email = "test@example.com";
        body.password = "secret123";
        body.userType = UserType.STUDENT;

        when(userRepository.findByEmail(body.email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(body.password)).thenReturn("hashed-secret");

        userService.register(body);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
        User saved = captor.getValue();

        assertEquals("Test User", saved.getName());
        assertEquals("test@example.com", saved.getEmail());
        assertEquals("hashed-secret", saved.getPasswordHash());
        assertEquals(UserType.STUDENT, saved.getUserType());
        assertEquals(PlanType.FREE, saved.getPlanType());
        assertEquals(AccountStatus.ACTIVE, saved.getStatus());
    }

    @Test
    void login_success_returns_token() {
        LoginRequestDTO body = new LoginRequestDTO();
        body.email = "login@example.com";
        body.password = "mypassword";

        User user = new User();
        UUID id = UUID.randomUUID();
        user.setIdUser(id);
        user.setName("Login User");
        user.setEmail(body.email);
        user.setPasswordHash("hashed");
        user.setUserType(UserType.STUDENT);
        user.setPlanType(PlanType.FREE);
        user.setStatus(AccountStatus.ACTIVE);

        when(userRepository.findByEmail(body.email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(body.password, user.getPasswordHash())).thenReturn(true);

        ResponseEntity<?> resp = userService.login(body);
        assertEquals(200, resp.getStatusCodeValue());
        assertTrue(resp.getBody() instanceof ResponseDTO);
        ResponseDTO dto = (ResponseDTO) resp.getBody();
        assertEquals(user.getIdUser(), dto.idUser());
        assertEquals("token-" + id.toString(), dto.token());
        assertEquals(user.getEmail(), dto.email());
    }

}
