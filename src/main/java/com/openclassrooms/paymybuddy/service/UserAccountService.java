package com.openclassrooms.paymybuddy.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * Service pour la gestion des comptes utilisateurs.
 */
@Service
@AllArgsConstructor
public class UserAccountService implements AccountManagementInterface {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * Crée un compte utilisateur
     * 
     * @param newUser Le nouvel utilisateur à créer
     */
    @Override
    @Transactional
    public void createAccount(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())
                || userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistsException("Utilisateur existant");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

    /**
     * Met à jour les informations d'un utilisateur (mot de passe).
     *
     * @param newUser l'utilisateur avec les nouvelles infos
     */
    @Override
    @Transactional
    public void updateUserInfos(User newUser) {
        // Encodage du password
        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

}
