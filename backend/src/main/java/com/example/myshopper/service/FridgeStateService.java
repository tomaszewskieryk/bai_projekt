package com.example.myshopper.service;

import com.example.myshopper.repository.FridgeStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FridgeStateService {

    private final FridgeStateRepository fridgeStateRepository;

    @Autowired
    public FridgeStateService(FridgeStateRepository fridgeStateRepository) {
        this.fridgeStateRepository = fridgeStateRepository;
    }

    public void createFridgeStateForNewUser(int userID) {
        int fridgeStateID = fridgeStateRepository.saveFridgeStateForNewUser(userID);
        log.info("Created fridge state with id = " + fridgeStateID);
    }
}
