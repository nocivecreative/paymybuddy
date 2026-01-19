package com.openclassrooms.paymybuddy.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // No + All : Assure la stabilité du bonding Spring
@AllArgsConstructor
public class NewTransactionDTO {

    private int userId;
    private int friendId;
    private BigDecimal amount;
    private String description;

}
