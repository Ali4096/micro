package com.seeder.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cashkick-service")
public interface CashkickClient {

    @GetMapping("/homes")
    String getHome();
}
