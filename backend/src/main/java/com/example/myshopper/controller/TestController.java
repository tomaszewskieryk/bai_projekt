package com.example.myshopper.controller;

import com.example.myshopper.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    public User testEndpoint(){
        return new User("erykulus", "ercio12@wp.pl");
    }
}
