package com.seeder.user_service.services;

import com.seeder.user_service.dtos.UserDTO;
import com.seeder.user_service.dtos.UserWithOnlyIdDTO;
import com.seeder.user_service.entities.User;
import com.seeder.user_service.entities.UserCredit;
import com.seeder.user_service.exceptions.ServiceException;
import com.seeder.user_service.exceptions.UserAlreadyExistException;
import com.seeder.user_service.exceptions.UserNotFoundException;
import com.seeder.user_service.rapositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public User registerUser(UserDTO userDTO) {
        // Validate the username here, instead of in the controller
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            log.info("Username is required");
            throw new IllegalArgumentException("Username is required");
        }

        // Check if the username or email already exists
        Optional<User> existingUserByUserName = userRepository.findByUsername(userDTO.getUsername());
        if (existingUserByUserName.isPresent()) {
            log.info("User with username {} already exists", userDTO.getUsername());
            throw new UserAlreadyExistException("User Name already exists");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserByEmail.isPresent()) {
            log.info("User with email {} already exists", userDTO.getEmail());
            throw new UserAlreadyExistException("Email already exists");
        }

        // If validation passed, map UserDTO to User entity and save it
        User user = modelMapper.map(userDTO, User.class);

        // Ensure UserCredit is set properly
        UserCredit userCredit = user.getUserCredit();
        if (userCredit != null) {
            // If UserCredit is part of the UserDTO, make sure to initialize it
            userCredit.setUser(user); // Set the back-reference for the UserCredit entity
        }

        try {
            // Persist the User and ensure that cascading works properly
            return userRepository.save(user); // This should save both User and UserCredit due to CascadeType.ALL
        } catch (Exception e) {
            log.error("Unexpected error while saving user", e);
            throw new ServiceException("Some unexpected error occurred");
        }

    }

    public UserDTO validateUser(UserDTO userDTO) {
        // Check if the username exists in the repository
        if (!userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserNotFoundException("User does not exist.");
        }

        // Fetch the user entity from the repository
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User does not exist."));

        // Map the User entity back to UserDTO and return
        return modelMapper.map(user, UserDTO.class);
    }


    public UserWithOnlyIdDTO validateUserById(Long userid) {
        Optional<User> user = userRepository.findById(userid);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not exist");
        } else {
            return modelMapper.map(user.get(), UserWithOnlyIdDTO.class);
        }
    }

}
