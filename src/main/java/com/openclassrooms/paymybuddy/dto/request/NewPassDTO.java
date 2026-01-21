package com.openclassrooms.paymybuddy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NewPassDTO {

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 12, message = "Minimum 12 caracteres")
    private final String password;

}
