package com.openclassrooms.paymybuddy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // No + All : Assure la stabilité du bonding Spring
@AllArgsConstructor
public class FindUserDTO {

    private String mail;

}
