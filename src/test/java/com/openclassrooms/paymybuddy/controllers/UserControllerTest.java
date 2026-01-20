package com.openclassrooms.paymybuddy.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.openclassrooms.paymybuddy.dto.request.NewPassDTO;
import com.openclassrooms.paymybuddy.dto.request.NewUserDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.UserAccountService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserAccountService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        lenient().when(securityUser.getId()).thenReturn(1);
        lenient().when(securityUser.getUsername()).thenReturn("testuser");
        lenient().when(securityUser.getEmail()).thenReturn("test@example.com");
    }

    @Test
    void home_called_returnsIndex() {
        // Arrange - nothing to arrange

        // Act
        String result = userController.home();

        // Assert
        assertEquals("index", result);
    }

    @Test
    void getSignup_called_returnsSignup() {
        // Arrange - nothing to arrange

        // Act
        String result = userController.getSignup();

        // Assert
        assertEquals("signup", result);
    }

    @Test
    void postSignup_validData_redirectsToLogin() {
        // Arrange
        NewUserDTO newUserDTO = new NewUserDTO("newuser", "newuser@example.com", "password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(userService).createAccount(any(User.class));

        // Act
        String result = userController.postSignup(newUserDTO, bindingResult);

        // Assert
        assertEquals("redirect:/login", result);
        verify(userService).createAccount(any(User.class));
    }

    @Test
    void postSignup_invalidData_returnsSignup() {
        // Arrange
        NewUserDTO newUserDTO = new NewUserDTO("", "", "");
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = userController.postSignup(newUserDTO, bindingResult);

        // Assert
        assertEquals("signup", result);
        verify(userService, never()).createAccount(any(User.class));
    }

    @Test
    void getProfil_authenticated_returnsProfil() {
        // Arrange - setup done in @BeforeEach

        // Act
        String result = userController.getMethodName(model, securityUser);

        // Assert
        assertEquals("profil", result);
        verify(model).addAttribute(eq("profil"), any());
    }

    @Test
    void postProfil_validPassword_redirectsToProfil() {
        // Arrange
        NewPassDTO newPassDTO = new NewPassDTO("newPassword123");
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(userService).updateUserInfos(any(User.class));

        // Act
        String result = userController.profilEdit(newPassDTO, securityUser, bindingResult);

        // Assert
        assertEquals("redirect:/profil", result);
        verify(userService).updateUserInfos(any(User.class));
    }

    @Test
    void postProfil_invalidData_returnsProfil() {
        // Arrange
        NewPassDTO newPassDTO = new NewPassDTO("");
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = userController.profilEdit(newPassDTO, securityUser, bindingResult);

        // Assert
        assertEquals("profil", result);
        verify(userService, never()).updateUserInfos(any(User.class));
    }
}
