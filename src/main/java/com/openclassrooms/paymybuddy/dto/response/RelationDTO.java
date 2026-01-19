package com.openclassrooms.paymybuddy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelationDTO {

    private final int id;
    private final String username;
    private final String email;

}
