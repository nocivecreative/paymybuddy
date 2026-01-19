package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.model.UserRelation;
import com.openclassrooms.paymybuddy.model.UserRelationId;

public interface UserRelationRepository extends JpaRepository<UserRelation, UserRelationId> {

    boolean existsByUserAndFriend(User user, User friend);

}
