package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;

/**
 * Interface pour la gestion des transactions entre utilisateurs.
 */
public interface TransactionManagementInterface {

    /**
     * Récupère toutes les transactions d'un utilisateur.
     *
     * @param currentUserId id de l'utilisateur connecté
     * @return la liste des transactions
     */
    public List<ExistingTransactionDTO> getAllTransactions(int currentUserId);

    /**
     * Effectue une transaction entre deux utilisateurs.
     *
     * @param currentUserId id de l'utilisateur qui envoie
     * @param idFriend      id de l'utilisateur qui reçoit
     * @param amount        montant à transférer
     * @param description   description de la transaction
     */
    public void doTransaction(int currentUserId, int idFriend, BigDecimal amount, String description);
}
