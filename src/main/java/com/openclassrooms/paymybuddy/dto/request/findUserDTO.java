package com.openclassrooms.paymybuddy.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FindUserDTO {

    private final String mail;

}
