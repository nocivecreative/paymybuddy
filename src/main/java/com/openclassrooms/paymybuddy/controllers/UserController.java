package com.openclassrooms.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.paymybuddy.dto.request.findUserDTO;
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
    public String tansfert() {
        return "transfert";
    }

    @GetMapping("/ajout-relation")
    public String addRelation() {
        return "ajout-relation";
    }

    @PostMapping("/ajout-relation")
    public String postMethodName(@RequestBody findUserDTO findUserDTO) {

        return null;
    }

}
