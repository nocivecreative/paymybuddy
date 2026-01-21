package com.openclassrooms.paymybuddy.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.FindUserDTO;
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
     * @return la vue ajout-relation
     */
    @GetMapping("/ajout-relation")
    public String getAddRelation() {
        return "ajout-relation";
    }

    /**
     * Ajoute une nouvelle relation pour l'utilisateur connecté.
     *
     * @param findUserDTO   données du formulaire avec l'email de l'ami
     * @param currentUser   l'utilisateur connecté
     * @param bindingResult résultat de la validation
     * @return redirection vers la page transfert
     */
    @PostMapping("/ajout-relation")
    public String postAddRelation(
            @ModelAttribute @Valid FindUserDTO findUserDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal SecurityUser currentUser) {

        if (bindingResult.hasErrors()) {
            return "ajout-relation";
        }

        relationService.addRelation(currentUser.getId(), findUserDTO.getMail());

        return "redirect:/transfert";
    }

}
