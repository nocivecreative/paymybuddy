package com.openclassrooms.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.paymybuddy.dto.response.RelationDTO;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class RelationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RelationService relationService;

    private User currentUser;
    private User friend;

    @BeforeEach
    void setUp() {
        currentUser = new User(1, "currentUser", "current@example.com", "password");
        currentUser.setFriends(new ArrayList<>());
        friend = new User(2, "friend", "friend@example.com", "password");
        friend.setFriends(new ArrayList<>());
    }

    @Test
    void addRelation_validUsers_savesRelation() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail("friend@example.com")).thenReturn(Optional.of(friend));
        when(userRepository.save(currentUser)).thenReturn(currentUser);

        // Act
        relationService.addRelation(1, "friend@example.com");

        // Assert
        verify(userRepository).save(currentUser);
        assertTrue(currentUser.getFriends().contains(friend));
    }

    @Test
    void addRelation_currentUserNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class,
            () -> relationService.addRelation(1, "friend@example.com"));
    }

    @Test
    void addRelation_friendNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class,
            () -> relationService.addRelation(1, "unknown@example.com"));
    }

    @Test
    void addRelation_sameUser_throwsIllegalArgumentException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail("current@example.com")).thenReturn(Optional.of(currentUser));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> relationService.addRelation(1, "current@example.com"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addRelation_relationAlreadyExists_throwsIllegalStateException() {
        // Arrange
        currentUser.getFriends().add(friend);
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail("friend@example.com")).thenReturn(Optional.of(friend));

        // Act & Assert
        assertThrows(IllegalStateException.class,
            () -> relationService.addRelation(1, "friend@example.com"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getRelations_existingUser_returnsRelationDTOList() {
        // Arrange
        currentUser.getFriends().add(friend);
        when(userRepository.findById(1)).thenReturn(Optional.of(currentUser));

        // Act
        List<RelationDTO> result = relationService.getRelations(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals("friend", result.get(0).getUsername());
        assertEquals("friend@example.com", result.get(0).getEmail());
    }

    @Test
    void getRelations_userNotFound_throwsUserNotFoundException() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> relationService.getRelations(999));
    }
}
