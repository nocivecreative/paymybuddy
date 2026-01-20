package com.openclassrooms.paymybuddy.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.NewPassDTO;
import com.openclassrooms.paymybuddy.dto.request.NewUserDTO;
import com.openclassrooms.paymybuddy.dto.response.UserProfilDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.RelationService;
import com.openclassrooms.paymybuddy.service.UserAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller pour gérer les utilisateurs (inscription, profil).
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserAccountService userService;
    private final RelationService relationService;

    /**
     * Affiche la page d'accueil.
     *
     * @return la vue index
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Affiche la page d'inscription.
     *
     * @return la vue signup
     */
    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

    /**
     * Traite l'inscription d'un nouvel utilisateur.
     *
     * @param newUserDto    données du formulaire d'inscription
     * @param bindingResult résultat de la validation
     * @return redirection vers login si succès, sinon retour sur signup
     */
    @PostMapping("/signup")
    public String postSignup(@Valid @ModelAttribute NewUserDTO newUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        User newUser = new User(
                newUserDto.getUsername(),
                newUserDto.getEmail(),
                newUserDto.getPassword());

        userService.createAccount(newUser);

        return "redirect:/login";
    }

    /**
     * Affiche le profil de l'utilisateur connecté.
     *
     * @param model       le modèle pour la vue
     * @param currentUser l'utilisateur connecté
     * @return la vue profil
     */
    @GetMapping("/profil")
    public String getMethodName(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        UserProfilDTO currentUserProfil = new UserProfilDTO(currentUser.getUsername(), currentUser.getEmail());

        model.addAttribute("profil", currentUserProfil);

        return "profil";
    }

    /**
     * Met à jour le mot de passe de l'utilisateur.
     *
     * @param newPass       données du formulaire avec le nouveau mot de passe
     * @param currentUser   l'utilisateur connecté
     * @param bindingResult résultat de la validation
     * @return redirection vers profil
     */
    @PostMapping("/profil")
    public String profilEdit(
            @ModelAttribute NewPassDTO newPass,
            @AuthenticationPrincipal SecurityUser currentUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "profil";
        }
        User newUserInfos = new User(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail(),
                newPass.getPassword());

        userService.updateUserInfos(newUserInfos);

        return "redirect:/profil";
    }

}
