package com.seeder.user_service.controller;

import com.seeder.user_service.controllers.UserController;
import com.seeder.user_service.dtos.UserCreditDTO;
import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.dtos.UserWithOnlyIdDTO;
import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.services.UserCreditService;
import com.seeder.user_service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserCreditService userCreditService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private User user;
    private UserCredit userCredit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a UserDTO and a User for testing
        userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@example.com");

        userCredit = new UserCredit();
        user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setUserCredit(userCredit);
    }

    @Test
    void createUser_shouldReturnCreatedStatus_whenUserIsCreated() {
        when(userService.registerUser(userDTO)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getUsername(), response.getBody().getUsername());
        verify(userService).registerUser(userDTO);
        verify(modelMapper).map(user, UserDTO.class);
    }

    @Test
    void validateUserCredit_shouldReturnOk_whenCreditIsSufficient() {
        long userId = 1L;
        double amount = 100.0;
        when(userCreditService.isCreditSufficient(userId, amount)).thenReturn(userCredit);
        when(modelMapper.map(userCredit, UserCreditDTO.class)).thenReturn(new UserCreditDTO());

        ResponseEntity<UserCreditDTO> response = userController.validateUserCredit(userId, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userCreditService).isCreditSufficient(userId, amount);
        verify(modelMapper).map(userCredit, UserCreditDTO.class);
    }

    @Test
    void validateUser_shouldReturnOk_whenUserExists() {
        long userId = 1L;
        UserWithOnlyIdDTO userWithOnlyIdDTO = new UserWithOnlyIdDTO();
        userWithOnlyIdDTO.setId(user.getId());
        when(userService.validateUserById(userId)).thenReturn(userWithOnlyIdDTO);

        ResponseEntity<UserWithOnlyIdDTO> response = userController.validateUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getId(), response.getBody().getId());
        verify(userService).validateUserById(userId);
    }

    @Test
    void deductCredit_shouldReturnOk_whenCreditIsDeducted() {
        long userId = 1L;
        double amount = 100.0;
        when(userCreditService.deductCredit(userId, amount)).thenReturn(true);

        ResponseEntity<String> response = userController.deductCredit(userId, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit deduction successful", response.getBody());
        verify(userCreditService).deductCredit(userId, amount);
    }

    @Test
    void deductCredit_shouldReturnBadRequest_whenInsufficientBalance() {
        long userId = 1L;
        double amount = 100.0;
        when(userCreditService.deductCredit(userId, amount)).thenReturn(false);

        ResponseEntity<String> response = userController.deductCredit(userId, amount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient balance", response.getBody());
        verify(userCreditService).deductCredit(userId, amount);
    }
}
