package com.seeder.contract_service.controllers;


import com.seeder.contract_service.dtos.ContractDTO;
import com.seeder.contract_service.services.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("contract-service")
public class Contract2Controller {
    ContractService contractService;
    @GetMapping("/contracts/validate")
    ResponseEntity<List<ContractDTO>> validateContracts(@RequestParam("contractIds") List<Long> contractIds) {
        List<ContractDTO>  contractDTOS = contractService.validateAndReturnContracts(contractIds);
        return ResponseEntity.ok(contractDTOS);
    }

    @PostMapping("/contracts/make-pending")
    public ResponseEntity<String> makeAllContractsPending(@RequestParam("contractIds") List<Long> contractIds) {
        // Call the service method to update the contract statuses to 'PENDING'
        contractService.makeAllContractsPending(contractIds);

        // Return a success message with status OK (200)
        return ResponseEntity.ok("All contracts have been successfully marked as pending.");
    }

}
