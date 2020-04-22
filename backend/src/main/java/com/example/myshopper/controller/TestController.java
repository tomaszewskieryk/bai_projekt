package com.example.myshopper.controller;

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

//    @GetMapping("test")
//    public String testEndpoint(){
//        return testRepository.getTest();
//    }

    @GetMapping("test")
    public User testEndpoint(){
        return userRepository.getTestUser();
    }

    @GetMapping("testUnauthorised")
    public String testUnauthorizedEndpoint(){
        return testRepository.getTest();
    }
}
