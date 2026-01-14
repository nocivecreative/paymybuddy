package com.openclassrooms.paymybuddy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/testuser")
    public String getUser() {
        return "Authentifié en tant qu'utilisateur";
    }

}
