package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
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

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
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
    public String getTransfert(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        populateTransfertModel(model, currentUser.getId());
        model.addAttribute("newTransactionDTO", new NewTransactionDTO());
        return "transfert";
    }

    /**
     * Effectue un transfert d'argent vers un ami.
     *
     * @param transaction   données de la transaction (ami, montant, description)
     * @param bindingResult résultat de la validation
     * @param model         le modèle pour la vue
     * @param currentUser   l'utilisateur connecté
     * @return redirection vers transfert
     */
    @PostMapping("/transfert")
    public String postTransfert(
            @Valid @ModelAttribute("newTransactionDTO") NewTransactionDTO transaction,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        if (bindingResult.hasErrors()) {
            populateTransfertModel(model, currentUser.getId());
            return "transfert";
        }

        try {
            transactionService.doTransaction(currentUser.getId(),
                    transaction.getFriendId(),
                    transaction.getAmount(),
                    transaction.getDescription());
        } catch (UserNotFoundException _) { // " _" = Unnamed Variables (Java 22+) : indique explicitement que
                                            // l'exception est
                                            // capturée mais jamais utilisée. On n'a pas besoin d'accéder à son message
                                            // ou sa stack trace.
            bindingResult.reject("user.notfound", "Erreur lors du transfert");
            populateTransfertModel(model, currentUser.getId());
            logger.error("Erreur lors du transfert");
            return "transfert";
        } catch (IllegalArgumentException _) {
            bindingResult.reject("amount.invalid", "Erreur lors du transfert");
            populateTransfertModel(model, currentUser.getId());
            logger.error("Erreur lors du transfert");
            return "transfert";
        }

        return "redirect:/transfert";
    }

    /**
     * Méthode utilitaire privée qui peuple le model avec les reltions et les
     * transaction d'un user
     * Cette methode est un helper qui évite la duplication de code
     * 
     * @param model  le modèle pour la vue
     * @param userId ID de l'utilisateur
     */
    private void populateTransfertModel(Model model, Integer userId) {
        List<RelationDTO> relations = relationService.getRelations(userId);
        List<ExistingTransactionDTO> transactions = transactionService.getAllTransactions(userId);
        model.addAttribute("relations", relations);
        model.addAttribute("transactions", transactions);
    }

}
