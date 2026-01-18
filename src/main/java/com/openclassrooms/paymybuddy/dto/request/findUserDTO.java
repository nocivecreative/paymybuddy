package com.openclassrooms.paymybuddy.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class findUserDTO {

    private final String email;

}
