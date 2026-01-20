package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelation;
import com.openclassrooms.paymybuddy.model.UserRelationId;

public interface UserRelationRepository extends JpaRepository<UserRelation, UserRelationId> {

    boolean existsByUserAndFriend(User user, User friend);

    List<UserRelation> findByUser(User user);

    // AVANT : Chaque appel a relation.getFriend() declenche une requete SQL
    // supplementaire car FetchType.LAZY
    // APRES : Une seule requête SQL / objet friend est déjà hydraté en mémoire
    @Query("SELECT ur FROM UserRelation ur JOIN FETCH ur.friend WHERE ur.user = :user")
    List<UserRelation> findByUserWithFriend(@Param("user") User user);

}
