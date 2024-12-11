package com.seeder.user_service.service;

import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.entities.User;
import com.seeder.user_service.rapositories.UserRepository;
import com.seeder.user_service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private User user;

    private UserDTO userdto;

    @BeforeEach
    void setUp() {
        // Initialize the mocks and create the test user
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "testUser", "password123","", null);
        userdto = modelMapper.map(user,UserDTO.class);

    }

    @Test
    void registerUserTest_shouldRegisterUser_whenUserDoesNotExist() {
        // Arrange: Define mock behavior for the repository
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);   //Use this methods
        when(userRepository.save(user)).thenReturn(user);     //USe this methods


        // Act: Call the method under test
        User result = userService.registerUser(userdto);

        // Assert: Check if the result is as expected
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

        // Verify that repository methods were called
        verify(userRepository).existsByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void registerUserTest_shouldThrowException_whenUserAlreadyExists() {
        // Arrange: Define mock behavior for the repository
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);  //Use this method

        // Act & Assert: Check if an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userdto));

        // Verify that repository methods were called
        verify(userRepository).existsByUsername(user.getUsername());
        verify(userRepository, never()).save(any(User.class)); // save shouldn't be called
    }

    @Test
    void validateUser_shouldReturnTrue_whenUserExists() {
        // Arrange: Mock the behavior of the repository
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.existsById(user.getId())).thenReturn(true);

        // Act: Call the validateUser method
        UserDTO result = userService.validateUser(userdto);

        // Assert: The result should be true since the user exists
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());

        // Verify the interaction with the repository
        verify(userRepository).existsByUsername(userdto.getUsername());
    }

    // Test case: User does not exist
    @Test
    void validateUser_shouldReturnFalse_whenUserDoesNotExist() {
        // Arrange: Mock the behavior of the repository
        when(userRepository.existsByUsername(userdto.getUsername())).thenReturn(false);

        // Act: Call the validateUser method
        UserDTO result = userService.validateUser(userdto);

        // Assert: The result should be false since the user doesn't exist
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());

        // Verify the interaction with the repository
        verify(userRepository).existsByUsername(userdto.getUsername());
    }

}
