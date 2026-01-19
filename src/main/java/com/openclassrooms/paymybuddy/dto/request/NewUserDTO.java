package com.openclassrooms.paymybuddy.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NewUserDTO {

    private final String username;
    private final String email;
    private final String password;

}
