package com.seeder.contract_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cashkick-service")
public interface CashkickClient {
//    @GetMapping("/cashkicks/{id}")
//    Cashkick getCashkickById(@PathVariable Long id);
}
