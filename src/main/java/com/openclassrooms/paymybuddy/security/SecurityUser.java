package com.openclassrooms.paymybuddy.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.paymybuddy.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Cette Class est crée uniquement pour différencier le type User de PayMyBuddy
 * du type User de spring security et éviter la confusion
 * (org.springframework.security.core.userdetails.User)
 * 
 * Il n'est pas prévu d'avoir des rôles en base, donc on hardcode un role "USER"
 */
@Data
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final int id;
    private final String username;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
