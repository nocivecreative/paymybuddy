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
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.security.SecurityUser;
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
     * @param model le modèle pour la vue
     * @return la vue signup
     */
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("newUserDTO", new NewUserDTO());
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
    public String postSignup(@Valid @ModelAttribute("newUserDTO") NewUserDTO newUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            userService.createAccount(new User(
                    newUserDto.getUsername(),
                    newUserDto.getEmail(),
                    newUserDto.getPassword()));
        } catch (UserAlreadyExistsException _) {
            bindingResult.reject("user.exists", "Erreur de création de compte");
            return "signup";
        }

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
    public String getProfil(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        populateProfilModel(model, currentUser);
        return "profil";
    }

    /**
     * Met à jour le mot de passe de l'utilisateur.
     *
     * @param newPassDTO    données du formulaire avec le nouveau mot de passe
     * @param bindingResult résultat de la validation
     * @param model         le modèle pour la vue
     * @param currentUser   l'utilisateur connecté
     * @return redirection vers profil
     */
    @PostMapping("/profil")
    public String postProfil(
            @Valid @ModelAttribute("newPassDTO") NewPassDTO newPassDTO,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        if (bindingResult.hasErrors()) {
            populateProfilModel(model, currentUser);
            return "profil";
        }

        User newUserInfos = new User(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail(),
                newPassDTO.getPassword());

        userService.updateUserInfos(newUserInfos);

        return "redirect:/profil";
    }

    /**
     * Méthode utilitaire privée qui peuple le model avec les données du currentUser
     * Cette methode est un helper qui évite la duplication de code
     * 
     * @param model  le modèle pour la vue
     * @param userId ID de l'utilisateur
     */
    private void populateProfilModel(Model model, SecurityUser currentUser) {
        UserProfilDTO currentUserProfil = new UserProfilDTO(currentUser.getUsername(), currentUser.getEmail());
        model.addAttribute("profil", currentUserProfil);
        model.addAttribute("newPassDTO", new NewPassDTO());
    }

}
