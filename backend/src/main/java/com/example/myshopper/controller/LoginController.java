package com.example.myshopper.controller;

import com.example.myshopper.model.User;
import com.example.myshopper.security.LoginRequest;
import com.example.myshopper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public int loginPost(@RequestBody LoginRequest credentials) {
        return userService.login(credentials);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.createUser(user);
    }
}
