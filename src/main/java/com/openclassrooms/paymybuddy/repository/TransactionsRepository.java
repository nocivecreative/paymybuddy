package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySenderId(Integer senderId);

}
