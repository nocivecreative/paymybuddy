package com.openclassrooms.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller pour la page de connexion.
 */
@Controller
public class LoginController {

    /**
     * Affiche la page de connexion.
     *
     * @return la vue login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
