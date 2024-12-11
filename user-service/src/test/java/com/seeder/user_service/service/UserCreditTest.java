package com.seeder.user_service.service;

import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.services.UserCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserCreditTest {

    private UserCredit userCredit;

    private UserCreditService userCreditService;

    @BeforeEach
    void setUp() {
        // Initialize the service and UserCredit object
        userCredit = new UserCredit(0,null,10.0,8.0);
    }
    @Test
    void isCreditGreaterThanAmount_shouldReturnTrue_whenAmountIsGreater() {
        // Arrange: Create a UserCredit object with amount 100.0


        // Act: Check if the UserCredit's amount is greater than 50.0
        boolean result = userCreditService.isCreditGreaterThanAmount(userCredit, 50.0);

        // Assert: The result should be true since 100.0 > 50.0
        assertTrue(result);
    }

    // Test case: UserCredit's amount is less than the passed amount
    @Test
    void isCreditGreaterThanAmount_shouldReturnFalse_whenAmountIsLess() {
        // Arrange: Create a UserCredit object with amount 30.0

        // Act: Check if the UserCredit's amount is greater than 50.0
        boolean result = userCreditService.isCreditGreaterThanAmount(userCredit, 50.0);

        // Assert: The result should be false since 30.0 < 50.0
        assertFalse(result);
    }
}
