package com.seeder.cashkick_service.services;


import com.seeder.cashkick_service.client.ContractClient;
import com.seeder.cashkick_service.client.UserClient;
import com.seeder.cashkick_service.dtos.CashkickDTO;
import com.seeder.cashkick_service.dtos.ContractDTO;
import com.seeder.cashkick_service.dtos.UserDTO;
import com.seeder.cashkick_service.entities.Cashkick;
import com.seeder.cashkick_service.repositories.CashkickRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashkickService {

    private final UserClient userClient;

    private final ContractClient contractClient;
    private final ModelMapper modelMapper;
    private final CashkickRepository cashkickRepository;


    public CashkickService(UserClient userClient, ContractClient contractClient, ModelMapper modelMapper, CashkickRepository cashkickRepository) {
        this.userClient = userClient;
        this.contractClient = contractClient;
        this.modelMapper = modelMapper;
        this.cashkickRepository = cashkickRepository;
    }

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
        ResponseEntity response =  contractClient.allContractsPending(contrctIda);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new IllegalArgumentException("Cannot make all Contracts Pending");
        }
    }

    public List<ContractDTO> validateContracts(List<Long> contractIds) {
        ResponseEntity<List<ContractDTO>> response = contractClient.validateContracts(contractIds);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Contracts Invalid");
        }
        return response.getBody();
    }


    public void createCashKick(CashkickDTO cashkickDTO){
        UserDTO userDTO = new UserDTO(cashkickDTO.getUserId());
        //validate user
        validateUser(userDTO);

        //Validate cashkicks if it is in pending
        List<ContractDTO> contractDTOS = validateContracts(cashkickDTO.getContractIds());

        //Add Total Sum
        double totalAmount = contractDTOS.stream()
                .mapToDouble(contractDTO -> contractDTO.getContractAmount())  // Extract the double values
                .sum();  // Sum the values

        //Deduct credit from user
        deductCredit(cashkickDTO.getUserId(),cashkickDTO.getTotalReceived());

        //Make all the contracts status pending
        makeAllContractsPending(cashkickDTO.getContractIds());

        Cashkick cashkick = modelMapper.map(cashkickDTO,Cashkick.class);

        cashkickRepository.save(cashkick);

    }

}
