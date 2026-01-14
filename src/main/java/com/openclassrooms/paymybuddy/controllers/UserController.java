package com.openclassrooms.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.SignupRequestDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Test GET
    @GetMapping("/listusers")
    public String users(Model model) {
        model.addAttribute("users", userService.listAllUserForTest());
        return "list-users"; // list-users.html
    }

    // Affichage du formulaire d'inscription
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Soumission du formulaire d'inscription
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute SignupRequestDTO signupRequest) {
        userService.createFromSignup(signupRequest);
        return "redirect:/signup-success";
    }

    // Confirmation d'inscription OK
    @GetMapping("/signup-success")
    public String signupSuccess() {
        return "signup-success";
    }

    // Vue du profil perso
    @GetMapping("/profil")
    public String profil() {
        return "profil";

    }
    
    // Edition du profil perso
    @GetMapping("/profil/edit")
    public String profilEdit() {
        return "profil-edit";
    }

}
