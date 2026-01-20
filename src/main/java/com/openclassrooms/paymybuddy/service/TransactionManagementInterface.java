package com.openclassrooms.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;

public interface TransactionManagementInterface {

    public List<ExistingTransactionDTO> getAllTransactions(int currentUserId);

    public void doTransaction(int currentUserId, int idFriend, BigDecimal amount, String description);
}
