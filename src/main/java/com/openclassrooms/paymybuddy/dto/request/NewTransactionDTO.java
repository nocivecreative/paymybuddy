package com.openclassrooms.paymybuddy.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // No + All : Assure la stabilité du bonding Spring
@AllArgsConstructor
public class NewTransactionDTO {

    @Min(value = 1, message = "L'id de l'utilisateur source est obligatoire")
    private int userId;

    @Min(value = 1, message = "L'id de l'utilisateur de destination est obligatoire")
    private int friendId;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", inclusive = true, message = "Le montant doit être strictement positif")
    private BigDecimal amount;

    private String description;

}
