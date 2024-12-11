package com.seeder.user_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homes")
public class Home {

    @GetMapping
    public String home(){
        return "In home controller of user";
    }
}
