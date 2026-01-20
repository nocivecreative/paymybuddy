package com.openclassrooms.paymybuddy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service personnalisé pour charger les utilisateurs dans Spring Security.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

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

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + username));

        return new SecurityUser(user);
    }

}
