package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;

/**
 * Interface pour la gestion des comptes utilisateurs.
 */
public interface AccountManagementInterface {

    /**
     * Crée un nouveau compte utilisateur.
     *
     * @param newUser l'utilisateur à créer
     */
    public void createAccount(User newUser);

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param newUser l'utilisateur avec les nouvelles infos
     */
    public void updateUserInfos(User newUser);

}
