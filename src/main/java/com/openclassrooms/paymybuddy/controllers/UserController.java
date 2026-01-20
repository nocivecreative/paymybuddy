package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.dto.request.FindUserDTO;
import com.openclassrooms.paymybuddy.dto.request.NewPassDTO;
import com.openclassrooms.paymybuddy.dto.request.NewTransactionDTO;
import com.openclassrooms.paymybuddy.dto.request.NewUserDTO;
import com.openclassrooms.paymybuddy.dto.response.ExistingTransactionDTO;
import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.dto.response.UserProfilDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "signup";
    }

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

    // Vue du profil perso
    @GetMapping("/profil")
    public String getMethodName(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        UserProfilDTO currentUserProfil = new UserProfilDTO(currentUser.getUsername(), currentUser.getEmail());

        model.addAttribute("profil", currentUserProfil);

        return "profil";
    }

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

    @GetMapping("/transfert")
    public String tansfert(
            Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {

        List<RelationDTO> relations = userService.getRelations(currentUser.getId());

        List<ExistingTransactionDTO> transactions = userService.getAllTransactions(currentUser.getId());

        model.addAttribute("relations", relations);
        model.addAttribute("transactions", transactions);
        return "transfert";
    }

    @PostMapping("/transfert")
    public String transfert(
            @ModelAttribute NewTransactionDTO transaction,
            @AuthenticationPrincipal SecurityUser currenUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "transfert";
        }

        userService.doTranscation(currenUser.getId(),
                transaction.getFriendId(),
                transaction.getAmount(),
                transaction.getDescription());

        return "redirect:/transfert";
    }

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

        userService.addRelation(currentUser.getId(), findUserDTO.getMail());

        return "redirect:/transfert";
    }

}
