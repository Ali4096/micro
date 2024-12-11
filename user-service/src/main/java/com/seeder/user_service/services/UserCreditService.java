package com.seeder.user_service.services;

import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.rapositories.UserCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCreditService {

    @Autowired
    private UserCreditRepository userCreditRepository;


    public boolean isCreditGreaterThanAmount(UserCredit userCredit, double amount) {
        if (userCredit == null) {
            return false;
        }
        return userCredit.getBalance() > amount;
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
            return false; // Failure: Insufficient balance
        }
    }
}
