package com.transportadora.user.service;

import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import com.transportadora.user.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existsUser = userRepository.findByUsername(username);
        User user = existsUser.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return UserPrincipal.create(user);
    }

}
