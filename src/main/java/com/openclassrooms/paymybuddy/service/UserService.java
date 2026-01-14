package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.request.SignupRequestDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listAllUserForTest() {
        return userRepository.findAll();
    }

    public User createFromSignup(SignupRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // TODO : Hash

        if (!userRepository.existsByEmail(user.getEmail()) && !userRepository.existsByUsername(user.getUsername())) {
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("Utilisateur existant");
        }

    }

}
