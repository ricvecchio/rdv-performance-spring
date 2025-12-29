package com.rdv.perfomance.user.service;

import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.repository.UserRepository;
import com.rdv.perfomance.user.security.UserPrincipal;
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
        Optional<User> existsUser = userRepository.findByEmail(username);
        User user = existsUser.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return UserPrincipal.create(user);
    }

}
