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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping("/ajout-relation")
    public String getAddRelation() {
        return "ajout-relation";
    }

    @PostMapping("/ajout-relation")
    public String postAddRelation(
            @ModelAttribute FindUserDTO findUserDTO,
            @AuthenticationPrincipal SecurityUser currentUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "transfert";
        }

        relationService.addRelation(currentUser.getId(), findUserDTO.getMail());

        return "redirect:/transfert";
    }

}
