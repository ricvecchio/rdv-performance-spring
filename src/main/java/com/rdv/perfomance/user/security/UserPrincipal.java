package com.rdv.perfomance.user.security;

import com.rdv.perfomance.user.entities.AccountStatus;
import com.rdv.perfomance.user.entities.User;
import com.rdv.perfomance.user.entities.UserType;
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
    private final String email;
    private final String passwordHash;
    private final AccountStatus status;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.idUser = user.getIdUser();
        this.name = user.getName();
        this.email = user.getEmail();
        this.passwordHash = user.getPasswordHash();
        this.status = user.getStatus();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        UserType userType = user.getUserType();
        if (userType != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userType.name()));
        }
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
        return email;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != AccountStatus.EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != AccountStatus.CANCELED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == AccountStatus.ACTIVE;
    }
}
