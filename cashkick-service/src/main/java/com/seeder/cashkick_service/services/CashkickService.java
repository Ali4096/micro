package com.seeder.cashkick_service.services;


import com.seeder.cashkick_service.client.ContractClient;
import com.seeder.cashkick_service.client.UserClient;
import com.seeder.cashkick_service.dtos.CashkickDTO;
import com.seeder.cashkick_service.dtos.UserDTO;
import com.seeder.cashkick_service.entities.Cashkick;
import com.seeder.cashkick_service.repositories.CashkickRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashkickService {
    @Autowired
    UserClient userClient;

    @Autowired
    ContractClient contractClient;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CashkickRepository cashkickRepository;
    List<CashkickDTO> getAllCashKick(UserDTO userDTO){
        //Valide User
        UserDTO userDTO1 = validateUser(userDTO);

        // Proceed with fetching Cashkick details for the valid user
        List<Cashkick> cashkicks = cashkickRepository.findAllByUserId(userDTO.getId());

        // Convert Cashkick entities to CashkickDTO
        List<CashkickDTO> cashkickDTOs = cashkicks.stream()
                .map(cashkick -> {
                    return modelMapper.map(cashkick,CashkickDTO.class);
                })
                .collect(Collectors.toList());

        return cashkickDTOs; // Return the DTO object
    }

    UserDTO validateUser(UserDTO userDTO){
        ResponseEntity<UserDTO> userResponse = userClient.validateUser(userDTO.getId());

        if (userResponse.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Invalid user");
        }

        return userResponse.getBody();
    }

    void deductCredit(Long userId, Double amount){
        ResponseEntity<String> response = userClient.deductAmount(userId,amount);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new IllegalArgumentException("Not Deducted");
        }
    }

    void makeAllContractsPending(List<Long> contrctIda){
        contractClient.allContractsPending(contrctIda);
    }

    void validateContracts(List<Long> contrctIda){
        contractClient.allContractsPending(contrctIda);
    }


    void createCashKick(CashkickDTO cashkickDTO){
        UserDTO userDTO = new UserDTO(cashkickDTO.getUser_id());
        //validate user
        validateUser(userDTO);

        //Validate cashkicks if it is in pending
        validateContracts(cashkickDTO.getContractIds());
        //Add Total Sum

        //Deduct credit from user
        deductCredit(cashkickDTO.getUser_id(),cashkickDTO.getTotalReceived());

        //Make all the contracts status pending
        makeAllContractsPending(cashkickDTO.getContractIds());

    }


}
