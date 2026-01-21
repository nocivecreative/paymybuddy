package com.openclassrooms.paymybuddy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewUserDTO {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Entre 3 et 50 caracteres")
    private final String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private final String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 12, message = "Minimum 12 caracteres")
    private final String password;

}
