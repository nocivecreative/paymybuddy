package com.openclassrooms.paymybuddy.controllers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.openclassrooms.paymybuddy.dto.request.NewTransactionDTO;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.RelationService;
import com.openclassrooms.paymybuddy.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private RelationService relationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        lenient().when(securityUser.getId()).thenReturn(1);
    }

    @Test
    void getTransfert_authenticated_returnsTransfertView() {
        // Arrange
        when(relationService.getRelations(anyInt())).thenReturn(List.of());
        when(transactionService.getAllTransactions(anyInt())).thenReturn(List.of());

        // Act
        String result = transactionController.getTransfert(model, securityUser);

        // Assert
        assertEquals("transfert", result);
        verify(model).addAttribute(eq("relations"), any());
        verify(model).addAttribute(eq("transactions"), any());
        verify(model).addAttribute(eq("newTransactionDTO"), any(NewTransactionDTO.class));
    }

    @Test
    void postTransfert_validData_redirectsToTransfert() {
        // Arrange
        NewTransactionDTO transaction = new NewTransactionDTO(1, 2, new BigDecimal("50.00"), "Test");
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(transactionService).doTransaction(anyInt(), anyInt(), any(BigDecimal.class), any());

        // Act
        String result = transactionController.postTransfert(transaction, bindingResult, model, securityUser);

        // Assert
        assertEquals("redirect:/transfert", result);
        verify(transactionService).doTransaction(anyInt(), anyInt(), any(BigDecimal.class), any());
    }

    @Test
    void postTransfert_invalidData_returnsTransfert() {
        // Arrange
        NewTransactionDTO transaction = new NewTransactionDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = transactionController.postTransfert(transaction, bindingResult, model, securityUser);

        // Assert
        assertEquals("transfert", result);
        verify(transactionService, never()).doTransaction(anyInt(), anyInt(), any(BigDecimal.class), any());
    }
}
