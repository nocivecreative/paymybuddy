package com.openclassrooms.paymybuddy.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

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
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()
                || userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Utilisateur existant");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void updateUserInfos(User newUser) {
        // Encodage du password
        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

}
