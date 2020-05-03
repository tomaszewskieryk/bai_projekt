package com.example.myshopper.controller;

import com.example.myshopper.model.User;
import com.example.myshopper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fullUser")
    public User getFridgeStateByID() {
        return userService.getExampleUser();
    }
}
