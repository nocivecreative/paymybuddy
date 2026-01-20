package com.openclassrooms.paymybuddy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // No + All : Assure la stabilité du bonding Spring
@AllArgsConstructor
public class FindUserDTO {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String mail;

}
