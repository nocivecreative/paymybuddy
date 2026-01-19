package com.openclassrooms.paymybuddy.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(
            UserAlreadyExistsException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());
        return "signup";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(
            UserNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());
        return "signup";
    }
}
