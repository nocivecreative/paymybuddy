package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionsRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionsRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        sender = new User(1, "sender", "sender@example.com", "password");
        receiver = new User(2, "receiver", "receiver@example.com", "password");
    }

    @Test
    void getAllTransactions_existingUser_returnsDTOList() {
        // Arrange
        Transaction transaction = new Transaction(sender, receiver, new BigDecimal("100.00"), "Test description");
        when(userRepository.findById(1)).thenReturn(Optional.of(sender));
        when(transactionRepository.findBySenderId(1)).thenReturn(List.of(transaction));

        // Act
        List<ExistingTransactionDTO> result = transactionService.getAllTransactions(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals("receiver", result.get(0).getRelationUserName());
        assertEquals("receiver@example.com", result.get(0).getRelationMail());
        assertEquals(new BigDecimal("100.00"), result.get(0).getAmount());
    }

    @Test
    void getAllTransactions_userNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> transactionService.getAllTransactions(999));
    }

    @Test
    void doTransaction_validData_savesTransaction() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2)).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        transactionService.doTransaction(1, 2, new BigDecimal("50.00"), "Payment");

        // Assert
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void doTransaction_nullAmount_throwsIllegalArgumentException() {
        // Arrange - nothing to arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> transactionService.doTransaction(1, 2, null, "Payment"));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void doTransaction_zeroAmount_throwsIllegalArgumentException() {
        // Arrange - nothing to arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> transactionService.doTransaction(1, 2, BigDecimal.ZERO, "Payment"));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void doTransaction_negativeAmount_throwsIllegalArgumentException() {
        // Arrange - nothing to arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> transactionService.doTransaction(1, 2, new BigDecimal("-10.00"), "Payment"));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void doTransaction_senderNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class,
            () -> transactionService.doTransaction(1, 2, new BigDecimal("50.00"), "Payment"));
    }

    @Test
    void doTransaction_receiverNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class,
            () -> transactionService.doTransaction(1, 2, new BigDecimal("50.00"), "Payment"));
    }
}
