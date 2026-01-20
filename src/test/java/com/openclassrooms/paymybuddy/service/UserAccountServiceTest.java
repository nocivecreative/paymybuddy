package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.openclassrooms.paymybuddy.exceptions.UserAlreadyExistsException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserAccountService userAccountService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "test@example.com", "password123");
    }

    @Test
    void createAccount_newUser_savesUserWithEncodedPassword() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userAccountService.createAccount(testUser);

        // Assert
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(encoder).encode("password123");
        verify(userRepository).save(testUser);
        assertEquals("encodedPassword", testUser.getPassword());
    }

    @Test
    void createAccount_existingUsername_throwsUserAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userAccountService.createAccount(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createAccount_existingEmail_throwsUserAlreadyExistsException() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userAccountService.createAccount(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUserInfos_validUser_savesWithEncodedPassword() {
        // Arrange
        User userToUpdate = new User(1, "testuser", "test@example.com", "newPassword");
        when(encoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        // Act
        userAccountService.updateUserInfos(userToUpdate);

        // Assert
        verify(encoder).encode("newPassword");
        verify(userRepository).save(userToUpdate);
        assertEquals("encodedNewPassword", userToUpdate.getPassword());
    }
}
