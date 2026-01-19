package com.openclassrooms.paymybuddy.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExistingTransactionDTO {

    private String relationUserName;
    private String relationMail;
    private BigDecimal amount;
    private String description;

}
