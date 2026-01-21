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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller pour gérer les transactions entre utilisateurs.
 */
@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final RelationService relationService;

    /**
     * Affiche la page de transfert avec les relations et transactions.
     *
     * @param model       le modèle pour la vue
     * @param currentUser l'utilisateur connecté
     * @return la vue transfert
     */
    @GetMapping("/transfert")
    public String transfert(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        List<RelationDTO> relations = relationService.getRelations(currentUser.getId());

        List<ExistingTransactionDTO> transactions = transactionService.getAllTransactions(currentUser.getId());

        model.addAttribute("relations", relations);
        model.addAttribute("transactions", transactions);
        return "transfert";
    }

    /**
     * Effectue un transfert d'argent vers un ami.
     *
     * @param transaction   données de la transaction (ami, montant, description)
     * @param currentUser   l'utilisateur connecté
     * @param bindingResult résultat de la validation
     * @return redirection vers transfert
     */
    @PostMapping("/transfert")
    public String transfert(
            @ModelAttribute @Valid NewTransactionDTO transaction,
            BindingResult bindingResult,
            @AuthenticationPrincipal SecurityUser currentUser) {

        if (bindingResult.hasErrors()) {
            return "transfert";
        }

        transactionService.doTransaction(currentUser.getId(),
                transaction.getFriendId(),
                transaction.getAmount(),
                transaction.getDescription());

        return "redirect:/transfert";
    }

}
