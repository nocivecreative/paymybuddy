package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.NewTransactionDTO;
import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;
import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.RelationService;
import com.openclassrooms.paymybuddy.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final RelationService relationService;

    @GetMapping("/transfert")
    public String tansfert(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        List<RelationDTO> relations = relationService.getRelations(currentUser.getId());

        List<ExistingTransactionDTO> transactions = transactionService.getAllTransactions(currentUser.getId());

        model.addAttribute("relations", relations);
        model.addAttribute("transactions", transactions);
        return "transfert";
    }

    @PostMapping("/transfert")
    public String transfert(
            @ModelAttribute NewTransactionDTO transaction,
            @AuthenticationPrincipal SecurityUser currenUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "transfert";
        }

        transactionService.doTransaction(currenUser.getId(),
                transaction.getFriendId(),
                transaction.getAmount(),
                transaction.getDescription());

        return "redirect:/transfert";
    }

}
