package com.seeder.user_service.controllers;

import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.entities.User;
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

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
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
}

