package com.seeder.user_service.controllers;

import com.seeder.user_service.client.ContractClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homes")
public class Home {

    private final ContractClient contractClient;

    public Home(ContractClient contractClient) {
        this.contractClient = contractClient;
    }

    @GetMapping
    public String home(){
        return "In home controller of user";
    }

    @GetMapping("/test")
    public String test(){
        return contractClient.getHome();
    }
}
