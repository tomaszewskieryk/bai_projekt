package com.example.myshopper.controller;

import com.example.myshopper.model.User;
import com.example.myshopper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LoginController {

    UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String loginPost() {
        return "login";
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
//        String email = user.getEmail();
        return userService.getSimpleCurrentUser("mati@test.com");
    }
}
