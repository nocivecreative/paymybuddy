package com.openclassrooms.paymybuddy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepositry;

    /**
     * Crée un Custom User pour Spring Security depuis les utilisateurs {@link User}
     * de la base de données
     * 
     * @param username le username de l'utilisateur en base de donnée
     * @return {@link SecurityUser}, implémentation de {@link UserDetails}
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepositry.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + username));

        return new SecurityUser(user);
    }

}
