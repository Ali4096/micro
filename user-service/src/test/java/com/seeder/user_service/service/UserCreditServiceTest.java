package com.seeder.user_service.service;

import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.exceptions.AmountExhaustedException;
import com.seeder.user_service.exceptions.UserNotFoundException;
import com.seeder.user_service.rapositories.UserCreditRepository;
import com.seeder.user_service.rapositories.UserRepository;
import com.seeder.user_service.services.UserCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCreditServiceTest {

    @Mock
    private UserCreditRepository userCreditRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCreditService userCreditService;

    private User user;
    private UserCredit userCredit;

    @BeforeEach
    public void setUp() {
        // Set up test data
        userCredit = new UserCredit(1L, null, 100.0, 50.0);  // User with balance 50
        user = new User(1L, "username", "user@example.com", "password", userCredit);
    }

    @Test
    public void testIsCreditSufficient_EnoughBalance() {
        // Mock the behavior of the repositories
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Test if the user has sufficient credit
        UserCredit result = userCreditService.isCreditSufficient(1L, 30.0);

        assertNotNull(result);
        assertEquals(50.0, result.getBalance());
    }

    @Test
    public void testIsCreditSufficient_InsufficientBalance() {
        // Mock the behavior of the repositories
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Test insufficient credit
        AmountExhaustedException thrown = assertThrows(AmountExhaustedException.class, () -> {
            userCreditService.isCreditSufficient(1L, 60.0);
        });

        assertEquals("Insufficient credit balance for the requested amount", thrown.getMessage());
    }

    @Test
    public void testIsCreditSufficient_UserNotFound() {
        // Mock the behavior of the repositories
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Test if the user is not found
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userCreditService.isCreditSufficient(1L, 30.0);
        });

        assertEquals("User does not exist", thrown.getMessage());
    }

    @Test
    public void testDeductCredit_SuccessfulDeduction() {
        // Mock the behavior of the repositories
        when(userCreditRepository.findById(1L)).thenReturn(Optional.of(userCredit));

        // Test if deduction is successful
        boolean result = userCreditService.deductCredit(1L, 30.0);

        assertTrue(result);
        assertEquals(20.0, userCredit.getBalance()); // Balance after deduction
    }

    @Test
    public void testDeductCredit_InsufficientBalance() {
        // Mock the behavior of the repositories
        when(userCreditRepository.findById(1L)).thenReturn(Optional.of(userCredit));

        // Test insufficient balance
        AmountExhaustedException thrown = assertThrows(AmountExhaustedException.class, () -> {
            userCreditService.deductCredit(1L, 60.0); // Balance is 50, so deduction fails
        });

        assertEquals("Insufficient credit balance for the requested amount", thrown.getMessage());
    }

    @Test
    public void testDeductCredit_UserNotFound() {
        // Mock the behavior of the repositories
        when(userCreditRepository.findById(1L)).thenReturn(Optional.empty());

        // Test if user is not found
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userCreditService.deductCredit(1L, 30.0);
        });

        assertEquals("User not found", thrown.getMessage());
    }
}
