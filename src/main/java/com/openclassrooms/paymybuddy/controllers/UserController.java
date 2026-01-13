package com.openclassrooms.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/listusers")
    public String users(Model model) {
        model.addAttribute("users", userService.listAllUserForTest());
        return "list-users"; // list-users.html
    }

    // Affichage du formulaire
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Soumission du formulaire
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:/signup-success";
    }

    @GetMapping("/signup-success")
    public String signupSuccess() {
        return "signup-success";
    }

}
