package com.example.myshopper.controller;

import com.example.myshopper.model.FridgeState;
import com.example.myshopper.service.FridgeStateService;
import com.example.myshopper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FridgeStateController {

    FridgeStateService fridgeStateService;
    UserService userService;

    @Autowired
    public FridgeStateController(FridgeStateService fridgeStateService, UserService userService) {
        this.fridgeStateService = fridgeStateService;
        this.userService = userService;
    }

    @GetMapping("/fridgeState/{fridgeStateID}")
    public FridgeState getFridgeStateByID(@PathVariable int fridgeStateID) {
        return fridgeStateService.getFridgeStateByID(fridgeStateID);
    }

    @GetMapping("/fridgeState/actual/{userID}")
    public FridgeState getActualFridgeStateByUserID(@PathVariable int userID) {
        return fridgeStateService.getActualFridgeStateByUserID(userID);
    }
}
