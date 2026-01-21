package com.openclassrooms.paymybuddy.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.openclassrooms.paymybuddy.dto.request.FindUserDTO;
import com.openclassrooms.paymybuddy.security.SecurityUser;
import com.openclassrooms.paymybuddy.service.RelationService;

@ExtendWith(MockitoExtension.class)
class RelationControllerTest {

    @Mock
    private RelationService relationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private SecurityUser securityUser;

    @InjectMocks
    private RelationController relationController;

    @BeforeEach
    void setUp() {
        lenient().when(securityUser.getId()).thenReturn(1);
    }

    @Test
    void getAddRelation_called_returnsAjoutRelationView() {
        // Arrange - nothing to arrange

        // Act
        String result = relationController.getAddRelation(model);

        // Assert
        assertEquals("ajout-relation", result);
    }

    @Test
    void postAddRelation_validEmail_redirectsToTransfert() {
        // Arrange
        FindUserDTO findUserDTO = new FindUserDTO("friend@example.com");
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(relationService).addRelation(anyInt(), anyString());

        // Act
        String result = relationController.postAddRelation(findUserDTO, bindingResult, securityUser);

        // Assert
        assertEquals("redirect:/transfert", result);
        verify(relationService).addRelation(anyInt(), anyString());
    }

    @Test
    void postAddRelation_invalidEmail_returnsTransfert() {
        // Arrange
        FindUserDTO findUserDTO = new FindUserDTO("");
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = relationController.postAddRelation(findUserDTO, bindingResult, securityUser);

        // Assert
        assertEquals("ajout-relation", result);
        verify(relationService, never()).addRelation(anyInt(), anyString());
    }
}
