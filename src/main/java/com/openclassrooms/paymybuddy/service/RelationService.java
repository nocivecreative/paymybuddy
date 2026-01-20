package com.openclassrooms.paymybuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelation;
import com.openclassrooms.paymybuddy.repository.UserRelationRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RelationService implements RelationManagementInterface {

    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    /**
     * Ajoute une relation entre l'utilisateur connecté à l'application et un
     * utilisateur enregistré via son adresse mail
     * 
     * <li>Vérifie si les deux utilisateuur existent
     * <li>Empêche de s'ajouter soi-même
     * <li>Vérifie si la raletion existe déjà <br>
     * 
     * @param currentUserId id de l'utilisateur connecté
     * @param friendEmail   adresse mail de l'utilisateur à ajouter
     * 
     * @throws UserNotFoundException    si l'utilisateur n'est pas trouvé
     * @throws IllegalArgumentException gère les autres erreurs
     */
    @Override
    @Transactional
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

    /**
     * Récupère la liste des relations existantes de l'utilisateur connecté
     * 
     * @param currentUserId l'id de l'utilisaateur connecté
     * @return La liste des realtions existantes
     */
    @Override
    @Transactional
    public List<RelationDTO> getRelations(int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        List<UserRelation> relationsList = userRelationRepository.findByUserWithFriend(user);

        return relationsList.stream()
                .map(relation -> new RelationDTO(
                        relation.getFriend().getId(),
                        relation.getFriend().getUsername(),
                        relation.getFriend().getEmail()))
                .toList();
    }

}
