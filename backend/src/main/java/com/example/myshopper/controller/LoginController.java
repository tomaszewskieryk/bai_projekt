package com.example.myshopper.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class LoginController {

    @PostMapping
    public String loginPost() {
        return "login";
    }
}
