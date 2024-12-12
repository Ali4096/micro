package com.seeder.cashkick_service.controllers;

import com.seeder.cashkick_service.dtos.CashkickDTO;
import com.seeder.cashkick_service.services.CashkickService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/cashkicks")
@Slf4j
public class CashkickController {

    @Autowired
    private CashkickService cashkickService;

    @PostMapping
    public ResponseEntity<String> createCashKick(@RequestBody CashkickDTO cashkickDTO) {
        log.info("Received request to create CashKick: {}", cashkickDTO);
        cashkickService.createCashKick(cashkickDTO);
        log.info("CashKick created successfully for: {}", cashkickDTO);
        return ResponseEntity.ok("CashKick created successfully.");
    }
}

