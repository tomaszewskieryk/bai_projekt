package com.example.myshopper.controller;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.exception.InternalException;
import com.example.myshopper.model.User;
import com.example.myshopper.repository.TestRepository;
import com.example.myshopper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Autowired
    public TestController(TestRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("test")
    public User testEndpoint() {
        return userRepository.getTestUser();
    }

    @GetMapping("testBadRequest")
    public User badRequestEndpoint() {
        throw new InputException("You're dumb!");
    }

    @GetMapping("testServerError")
    public User serverErrorEndpoint() {
        throw new InternalException("I'm dumb!");
    }

    @GetMapping("testUnauthorised")
    public String testUnauthorizedEndpoint() {
        return testRepository.getTest();
    }
}
