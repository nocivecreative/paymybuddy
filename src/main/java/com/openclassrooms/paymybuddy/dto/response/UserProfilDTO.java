package com.openclassrooms.paymybuddy.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserProfilDTO {

    private final String username;
    private final String email;

}
