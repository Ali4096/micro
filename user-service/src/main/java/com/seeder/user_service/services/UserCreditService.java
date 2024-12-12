package com.seeder.user_service.services;

import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.exceptions.AmountExhaustedException;
import com.seeder.user_service.exceptions.UserNotFoundException;
import com.seeder.user_service.rapositories.UserCreditRepository;
import com.seeder.user_service.rapositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCreditService {

    @Autowired
    private UserCreditRepository userCreditRepository;

    @Autowired
    private UserRepository userRepository;


    public UserCredit isCreditSufficient(long userId, double amount) {
        // Fetch user details
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User does not exist"));

        // Validate user credit
        if (user.getUserCredit().getBalance() < amount) {
            throw new AmountExhaustedException("Insufficient credit balance for the requested amount");
        }

        // Return true if validation passes
        return user.getUserCredit();
    }



    // Method to deduct a specified amount from the user's balance
    public boolean deductCredit(Long userId, double amount) {
        // Check for negative amount
        if (amount < 0) {
            throw new IllegalArgumentException("Deduction amount cannot be negative");
        }

        // Fetch the UserCredit by userId
        Optional<UserCredit> userCredit = userCreditRepository.findById(userId);


        // If user credit not found, return false
        if (userCredit.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        UserCredit userCredit1 = userCredit.get();

        // Check if the user has sufficient balance
        if (userCredit.get().getBalance() >= amount) {
            // Deduct the balance
            userCredit1.setBalance(userCredit1.getBalance() - amount);

            // Save the updated UserCredit to the repository (database)
            userCreditRepository.save(userCredit1);
            return true; // Success: Deduction was successful
        } else {
            throw new AmountExhaustedException("Insufficient credit balance for the requested amount");
        }
    }

}
