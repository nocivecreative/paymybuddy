package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public User create(User user) {
        return userRepository.save(user);
    }

}
