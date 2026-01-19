package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.FindUserDTO;
import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Vue du profil perso
    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }

    // TODO Edition du profil perso
    @GetMapping("/profil/edit")
    public String profilEdit() {
        return "profil-edit";
    }

    @GetMapping("/transfert")
    public String tansfert(
            Model model,
            @AuthenticationPrincipal SecurityUser securityUser) {

        List<RelationDTO> relations = userService.getRelations(securityUser.getId());

        model.addAttribute("relations", relations);

        return "transfert";
    }

    @GetMapping("/ajout-relation")
    public String getAddRelation() {
        return "ajout-relation";
    }

    @PostMapping("/ajout-relation")
    public String postAddRelation(
            @ModelAttribute FindUserDTO findUserDTO,
            @AuthenticationPrincipal SecurityUser securityUser) {

        userService.addRelation(securityUser.getId(), findUserDTO.getMail());

        return "redirect:/transfert";
    }

}
