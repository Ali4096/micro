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

import java.util.List;

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

    @GetMapping("/validate")
    public ResponseEntity<List<ContractDTO>> validateContracts(@RequestParam("contractIds") List<Long> contractIds) {
        List<ContractDTO>  contractDTOS = contractService.validateAndReturnContracts(contractIds);
        return ResponseEntity.ok(contractDTOS);
    }

    @PostMapping("/make-pending")
    public ResponseEntity<String> makeAllContractsPending(@RequestParam("contractIds") List<Long> contractIds) {
        // Call the service method to update the contract statuses to 'PENDING'
        contractService.makeAllContractsPending(contractIds);

        // Return a success message with status OK (200)
        return ResponseEntity.ok("All contracts have been successfully marked as pending.");
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

//    public ResponseEntity<?> getAllContractByUserId(@PathVariable("userId") Long userId){
//        return null;
//    }
//
//    public ResponseEntity<?> updateContractStatus(){
//        return null;
//    }

}









