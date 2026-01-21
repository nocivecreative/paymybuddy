package com.openclassrooms.paymybuddy.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.FindUserDTO;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.RelationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller pour gérer les relations entre utilisateurs.
 */
@Controller
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    /**
     * Affiche la page d'ajout de relation.
     *
     * @param model le modèle pour la vue
     * @return la vue ajout-relation
     */
    @GetMapping("/ajout-relation")
    public String getAddRelation(Model model) {
        model.addAttribute("findUserDTO", new FindUserDTO());
        return "ajout-relation";
    }

    /**
     * Ajoute une nouvelle relation pour l'utilisateur connecté.
     *
     * @param findUserDTO   données du formulaire avec l'email de l'ami
     * @param bindingResult résultat de la validation
     * @param currentUser   l'utilisateur connecté
     * @return redirection vers la page transfert
     */
    @PostMapping("/ajout-relation")
    public String postAddRelation(
            @Valid @ModelAttribute("findUserDTO") FindUserDTO findUserDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal SecurityUser currentUser) {

        if (bindingResult.hasErrors()) {
            return "ajout-relation";
        }

        try {
            relationService.addRelation(currentUser.getId(), findUserDTO.getMail());
        } catch (UserNotFoundException _) {
            bindingResult.reject("user.notfound", "Erreur lors de l'ajout de la relation");
            return "ajout-relation";
        } catch (IllegalArgumentException _) {
            bindingResult.reject("relation.invalid", "Erreur lors de l'ajout de la relation");
            return "ajout-relation";
        } catch (IllegalStateException _) {
            bindingResult.reject("relation.exists", "Erreur lors de l'ajout de la relation");
            return "ajout-relation";
        }

        return "redirect:/transfert";
    }

}
