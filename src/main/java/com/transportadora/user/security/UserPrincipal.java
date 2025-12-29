package com.transportadora.user.security;

import com.transportadora.user.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private final UUID idUser;
    private final String name;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.idUser = user.getIdUser();
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


