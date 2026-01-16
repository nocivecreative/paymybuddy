package com.openclassrooms.paymybuddy.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

/**
 * Cette Class est crée uniquement pour différencier le type User de PayMyBuddy
 * du type User de spring security et éviter la confusion
 * (org.springframework.security.core.userdetails.User)
 * 
 * Il n'est pas prévu d'avoir des rôles en base, donc on hardcode un role "USER"
 */
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final com.openclassrooms.paymybuddy.model.User user;

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
