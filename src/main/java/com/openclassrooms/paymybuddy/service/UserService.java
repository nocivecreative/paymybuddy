package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelation;
import com.openclassrooms.paymybuddy.repository.UserRelationRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    public List<User> listAllUserForTest() { // TODO remove après tests
        return userRepository.findAll();
    }

    public void addRelation(int currentUserId, String friendEmail) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable : ID=" + currentUserId));

        User friend = userRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable : mail=" + friendEmail));

        if (currentUser.getId().equals(friend.getId())) {
            throw new IllegalArgumentException("Impossible de s'ajouter soi-même");
        }

        if (userRelationRepository.existsByUserAndFriend(currentUser, friend)) {
            throw new IllegalStateException("Relation déjà existante");
        }

        UserRelation relation = new UserRelation(currentUser, friend);
        userRelationRepository.save(relation);

    }

}
