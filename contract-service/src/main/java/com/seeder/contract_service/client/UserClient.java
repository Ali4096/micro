package com.seeder.contract_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
//    @GetMapping("/users/{id}")
//    User getUserById(@PathVariable Long id);

    //just wnt to push
}
