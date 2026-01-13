package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.paymybuddy.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

}
