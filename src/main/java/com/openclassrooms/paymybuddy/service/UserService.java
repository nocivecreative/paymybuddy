package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> listAllUserForTest() {
        return userRepository.findAll();
    }

}
