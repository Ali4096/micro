package com.seeder.cashkick_service.client;

import com.seeder.cashkick_service.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contract-service")
public interface ContractClient {

    @GetMapping("/homes")
    String getHome();

    @GetMapping("/contracts/validate")
    ResponseEntity<String> validateContracts(@RequestParam("contractIds") List<Long> contractIds);

    @PostMapping("/contracts/make-pending")
    ResponseEntity<String> allContractsPending(@RequestParam("contractIds") List<Long> contractIds);

}
