package com.seeder.contract_service.controllers;


import com.seeder.contract_service.dtos.ContractDTO;
import com.seeder.contract_service.dtos.CreateContractDTO;
import com.seeder.contract_service.exceptions.ContractNotFoundException;
import com.seeder.contract_service.services.ContractService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<CreateContractDTO> createContract(@Valid @RequestBody CreateContractDTO createContractDTO){
//        log.info("Inside Contract Controller create contract method");
        // Call the service layer to create the contract using the provided DTO
        CreateContractDTO createdContract = contractService.createContract(createContractDTO);

        // Return a ResponseEntity with the created contract and HTTP status CREATED
        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable("contractId") Long contractId) {
        try {
            ContractDTO contractDTO = contractService.fetchContractById(contractId);
            return ResponseEntity.ok(contractDTO);
        } catch (ContractNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<?> getAllContractByUserId(@PathVariable("userId") Long userId){
        return null;
    }

    public ResponseEntity<?> updateContractStatus(){
        return null;
    }

}









