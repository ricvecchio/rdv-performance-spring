package com.rdv.perfomance.user.security;

import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var subject = tokenService.validateToken(token);

        if (subject != null) {
            try {
                UUID id = UUID.fromString(subject);
                Optional<User> userOpt = userRepository.findById(id);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    var authentication = new UsernamePasswordAuthenticationToken(UserPrincipal.create(user), null, UserPrincipal.create(user).getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (IllegalArgumentException ex) {
                // token subject not a UUID, ignore
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
