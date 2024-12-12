package com.seeder.user_service.service;

import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.dtos.UserWithOnlyIdDTO;
import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.exceptions.ServiceException;
import com.seeder.user_service.exceptions.UserAlreadyExistException;
import com.seeder.user_service.exceptions.UserNotFoundException;
import com.seeder.user_service.rapositories.UserRepository;
import com.seeder.user_service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

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
    void registerUser_shouldThrowIllegalArgumentException_whenUsernameIsNull() {
        userDTO.setUsername(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userDTO);
        });

        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    void registerUser_shouldThrowIllegalArgumentException_whenUsernameIsEmpty() {
        userDTO.setUsername(" ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userDTO);
        });

        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    void registerUser_shouldThrowUserAlreadyExistException_whenUsernameExists() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));

        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> {
            userService.registerUser(userDTO);
        });

        assertEquals("User Name already exists", exception.getMessage());
    }

    @Test
    void registerUser_shouldThrowUserAlreadyExistException_whenEmailExists() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> {
            userService.registerUser(userDTO);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void registerUser_shouldSaveUser_whenValid() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.registerUser(userDTO);

        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        verify(userRepository).save(user);
        verify(userRepository).findByUsername(userDTO.getUsername());
        verify(userRepository).findByEmail(userDTO.getEmail());
    }

    @Test
    void registerUser_shouldThrowServiceException_whenUnexpectedErrorOccurs() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(new RuntimeException("Database error"));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            userService.registerUser(userDTO);
        });

        assertEquals("Some unexpected error occurred", exception.getMessage());
    }

    @Test
    void registerUser_shouldSetUserCreditProperly_whenUserCreditIsNotNull() {
        // Arrange: Set user credit in DTO
        userDTO.setUserCredit(new UserCredit());
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.registerUser(userDTO);

        // Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserCredit());
        assertEquals(user, savedUser.getUserCredit().getUser());
    }

    @Test
    void validateUserById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        Long userId = 1L;

        // Mock the userRepository to return an empty Optional
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assert that the UserNotFoundException is thrown
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.validateUserById(userId);
        });

        assertEquals("User not exist", exception.getMessage());
    }

    @Test
    void validateUserById_shouldReturnUserWithOnlyIdDTO_whenUserExists() {
        Long userId = 1L;

        // Mock the userRepository to return a user
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock the modelMapper to return the DTO
        UserWithOnlyIdDTO userWithOnlyIdDTO = new UserWithOnlyIdDTO();
        userWithOnlyIdDTO.setId(user.getId());
        when(modelMapper.map(user, UserWithOnlyIdDTO.class)).thenReturn(userWithOnlyIdDTO);

        // Call the method and assert the result
        UserWithOnlyIdDTO result = userService.validateUserById(userId);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository).findById(userId);
        verify(modelMapper).map(user, UserWithOnlyIdDTO.class);
    }
}
