package com.seeder.cashkick_service.client;

import com.seeder.cashkick_service.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/homes")
    String getHome();

    @GetMapping("/api/users/validate-user/{userid}")
    ResponseEntity<UserDTO> validateUser(@PathVariable("userid") Long userId);

    @PostMapping("/api/users/deduct")
    ResponseEntity deductAmount(@RequestParam Long userId, @RequestParam Double amount);
}
