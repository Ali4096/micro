package com.seeder.cashkick_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "contract-service")
public interface ContractClient {

    @GetMapping("/homes")
    String getHome();
}
