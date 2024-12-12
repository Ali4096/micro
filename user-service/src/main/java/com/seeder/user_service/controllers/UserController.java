package com.seeder.user_service.controllers;

import com.seeder.user_service.dtos.UserCreditDTO;
import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.dtos.UserWithOnlyIdDTO;
import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.services.UserCreditService;
import com.seeder.user_service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserCreditService userCreditService;

    public UserController(UserService userService, ModelMapper modelMapper, UserCreditService userCreditService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userCreditService = userCreditService;
    }



    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        // Call the service to register the user
        User createdUser = userService.registerUser(userDTO);

        // Map the User entity back to a UserDTO
        UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);

        // Return the response entity with status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @GetMapping("/validate-credit")
    public ResponseEntity<UserCreditDTO> validateUserCredit(@RequestParam long userId, @RequestParam double amount) {
        // Call the service to validate user credit
        UserCredit userCredit = userCreditService.isCreditSufficient(userId, amount);

        return new ResponseEntity<>(modelMapper.map(userCredit,UserCreditDTO.class),HttpStatus.OK);
    }


    @GetMapping("/validate-user/{userid}")
    public ResponseEntity<UserWithOnlyIdDTO> validateUser(@PathVariable("userid") Long userid){
        UserWithOnlyIdDTO userWithOnlyIdDTO = userService.validateUserById(userid);
        return ResponseEntity.ok(userWithOnlyIdDTO);
    }


    @PostMapping("/deduct")
    public ResponseEntity<String> deductCredit(@RequestParam Long userId, @RequestParam double amount) {
        // Call the service to deduct credit
        boolean isDeducted = userCreditService.deductCredit(userId, amount);

        if (isDeducted) {
            return ResponseEntity.ok("Credit deduction successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
    }

}

