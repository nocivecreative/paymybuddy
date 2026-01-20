package com.openclassrooms.paymybuddy.service;

import java.util.List;

import com.openclassrooms.paymybuddy.dto.response.RelationDTO;

/**
 * Interface pour la gestion des relations entre utilisateurs.
 */
public interface RelationManagementInterface {

    /**
     * Ajoute une relation entre l'utilisateur connecté et un ami.
     *
     * @param currentUserId id de l'utilisateur connecté
     * @param friendEmail   email de l'ami à ajouter
     */
    public void addRelation(int currentUserId, String friendEmail);

    /**
     * Récupère la liste des relations d'un utilisateur.
     *
     * @param currentUserId id de l'utilisateur connecté
     * @return la liste des relations
     */
    public List<RelationDTO> getRelations(int currentUserId);
}
