package com.example.myshopper.controller;

import com.example.myshopper.model.FridgeState;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.service.FridgeStateService;
import com.example.myshopper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class FridgeStateController {

    private final FridgeStateService fridgeStateService;
    private final UserService userService;

    @Autowired
    public FridgeStateController(FridgeStateService fridgeStateService, UserService userService) {
        this.fridgeStateService = fridgeStateService;
        this.userService = userService;
    }

    @GetMapping("/fridges/{fridgeStateID}")
    public FridgeState getFridgeStateByID(@PathVariable int fridgeStateID) {
        return fridgeStateService.getFridgeStateByID(fridgeStateID);
    }

    @GetMapping("/users/{userID}/actualfridge")
    public FridgeState getActualFridgeStateByUserID(@PathVariable int userID) {
        return fridgeStateService.getActualFridgeStateByUserID(userID);
    }

    @GetMapping("/users/{userID}/fridges")
    public List<FridgeState> getAllFridgeStatesByUserID(@PathVariable int userID) {
        return fridgeStateService.getAllFridgesByUserID(userID);
    }

    @PostMapping("/fridges")
    public void createNewFridgeState(@RequestBody FridgeStateEntity fridgeStateEntity) {
        fridgeStateService.createFridge(fridgeStateEntity);
    }

    @PutMapping("/fridges")
    public void updateFridgeStateName(@RequestBody FridgeState fridgeState) {
        fridgeStateService.updateFridgeName(fridgeState);
    }

    @DeleteMapping("/fridges/{fridgeStateID}")
    public void deleteFridgeState(@PathVariable int fridgeStateID) {
        fridgeStateService.deleteFridgeStateByID(fridgeStateID);
    }
}
