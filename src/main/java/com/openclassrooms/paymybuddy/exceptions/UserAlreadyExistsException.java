package com.openclassrooms.paymybuddy.exceptions;

public class UserAlreadyExistsException extends RuntimeException  {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
