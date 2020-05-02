package com.example.myshopper.controller;

import com.example.myshopper.model.FridgeState;
import com.example.myshopper.service.FridgeStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FridgeStateController {

    FridgeStateService fridgeStateService;

    @Autowired
    public FridgeStateController(FridgeStateService fridgeStateService) {
        this.fridgeStateService = fridgeStateService;
    }

    @GetMapping("/fridgeState/{fridgeStateID}")
    public FridgeState getFridgeStateByID(@PathVariable int fridgeStateID) {
        return fridgeStateService.getFridgeStateByID(fridgeStateID);
    }
}
