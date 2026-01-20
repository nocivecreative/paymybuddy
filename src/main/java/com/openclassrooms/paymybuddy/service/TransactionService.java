package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionsRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * Service pour la gestion des transactions entre utilisateurs.
 */
@Service
@AllArgsConstructor
public class TransactionService implements TransactionManagementInterface {

    private final UserRepository userRepository;
    private final TransactionsRepository transactionRepository;

    /**
     * Récupère toutes les transactions envoyées par l'utilisateur.
     *
     * @param currentUserId id de l'utilisateur connecté
     * @return la liste des transactions
     */
    @Override
    public List<ExistingTransactionDTO> getAllTransactions(int currentUserId) {

        userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur source introuvable"));

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
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE, // Evite les lectures fantomes
            propagation = Propagation.REQUIRED)
    public void doTransaction(int currentUserId, int idFriend, BigDecimal amount, String description) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit etre positif");
        }

        User source = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur source introuvable"));

        User friend = userRepository.findById(idFriend)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur cible introuvable"));

        Transaction transaction = new Transaction(source, friend, amount, description);

        transactionRepository.save(transaction);

    }
}
