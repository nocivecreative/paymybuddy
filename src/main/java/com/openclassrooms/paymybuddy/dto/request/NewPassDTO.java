package com.openclassrooms.paymybuddy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPassDTO {

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 12, message = "Minimum 12 caracteres")
    private String password;

}
