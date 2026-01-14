package com.openclassrooms.paymybuddy.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignupRequestDTO {

    private String username;
    private String email;
    private String password;

}
