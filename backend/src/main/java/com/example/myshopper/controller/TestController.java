package com.example.myshopper.controller;

import com.example.myshopper.model.User;
import com.example.myshopper.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private TestRepository testRepository;

    @Autowired
    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("test")
//    public User testEndpoint(){
    public String testEndpoint(){
        return testRepository.getTest();
//        return new User("erykulus", "ercio12@wp.pl");
    }
}
