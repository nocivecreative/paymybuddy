package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.model.UserRelation;

public interface UserRelationRepository extends JpaRepository<UserRelation, Integer> {

}
