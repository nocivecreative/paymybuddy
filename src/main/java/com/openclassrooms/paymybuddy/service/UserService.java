package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;
import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelation;
import com.openclassrooms.paymybuddy.repository.TransactionsRepository;
import com.openclassrooms.paymybuddy.repository.UserRelationRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;
    private final TransactionsRepository transactionRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * Crée un compte utilisateur
     * 
     * @param newUser Le nouvel utilisateur à créer
     */
    public void createAccount(User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()
                || userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Utilisateur existant");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

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
    public List<RelationDTO> getRelations(int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable : ID=" + currentUserId));

        List<UserRelation> relationsList = userRelationRepository.findByUser(user);

        return relationsList.stream()
                .map(relation -> new RelationDTO(
                        relation.getFriend().getId(),
                        relation.getFriend().getUsername(),
                        relation.getFriend().getEmail()))
                .toList();
    }

    /**
     * Effectue une transaction entre l'uutilisateur connecté, et l'utilisateur
     * cible choisi.
     * 
     * @param currentUserId Utilisateur connecté
     * @param idFriend      Utilisateur cible
     * @param amount        Somme à transferer
     * @param description   Texte de description pour la transacrion
     * 
     * @throws UserNotFoundException Si l'utilisateur (source ou cible) n'est pas
     *                               trouvé
     */
    @Transactional
    public void doTranscation(int currentUserId, int idFriend, BigDecimal amount, String description) {

        User source = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur source introuvable : ID=" + currentUserId));

        User friend = userRepository.findById(idFriend)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur cible introuvable : ID=" + idFriend));

        Transaction transaction = new Transaction(source, friend, amount, description);

        transactionRepository.save(transaction);

    }

    public List<ExistingTransactionDTO> getAllTransactions(int currentUserId) {

        userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur source introuvable : ID=" + currentUserId));

        return transactionRepository
                .findBySenderId(currentUserId)
                .stream()
                .map(transaction -> new ExistingTransactionDTO(
                        transaction.getReceiver().getUsername(),
                        transaction.getReceiver().getEmail(),
                        transaction.getAmount(),
                        transaction.getDescription()))
                .toList();

    }

    @Transactional
    public void updateUserInfos(User newUser) {
        // Encodage du password
        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

}
