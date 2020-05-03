package com.example.myshopper.service;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.exception.InternalException;
import com.example.myshopper.model.FridgeState;
import com.example.myshopper.repository.FridgeStateRepository;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.transformer.FridgeStateTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FridgeStateService {

    private final FridgeStateRepository fridgeStateRepository;
    private final FridgeStateTransformer fridgeStateTransformer;

    @Autowired
    public FridgeStateService(FridgeStateRepository fridgeStateRepository, FridgeStateTransformer fridgeStateTransformer) {
        this.fridgeStateRepository = fridgeStateRepository;
        this.fridgeStateTransformer = fridgeStateTransformer;
    }

    public void createFridgeStateForNewUser(int userID) {
        FridgeStateEntity fridgeStateEntity = new FridgeStateEntity("actualState", true, userID);

        int fridgeStateID = fridgeStateRepository.saveFridgeStateForNewUser(fridgeStateEntity);
        log.info("Created fridgeState for with id = " + fridgeStateID + " for userID=" + userID);
    }

    public FridgeState getFridgeStateByID(int stateID) {
        FridgeStateEntity fridgeStateEntity = fridgeStateRepository.getFridgeStateEntityByID(stateID)
                .orElseThrow(() -> new InputException("Could not find any fridgeState with fridgeStateID=" + stateID));
        return fridgeStateTransformer.transformToFridgeState(fridgeStateEntity);
    }

    public FridgeState getActualFridgeStateByUserID(int userID) {
        FridgeStateEntity fridgeStateEntity = fridgeStateRepository.getActualFridgeStateEntityByUserID(userID)
                .orElseThrow(() -> new InternalException("No actual fridgeState for user with userID=" + userID));
        return fridgeStateTransformer.transformToFridgeState(fridgeStateEntity);
    }

    public void createFridge(FridgeStateEntity fridgeStateEntity) {
        fridgeStateEntity.setActual(false);
        int stateID = fridgeStateRepository.saveFridgeStateEntity(fridgeStateEntity);
        log.info("Created new fridgeState with id=" + stateID);
    }

    public List<FridgeState> getAllFridgesByUserID(int userID) {
        List<FridgeStateEntity> fridgeStatesEntities = fridgeStateRepository.getNotActualFridgeStatesEntitiesByUserID(userID);

        return fridgeStatesEntities.stream()
                .map(fridgeStateTransformer::transformToSimpleFridgeState)
                .collect(Collectors.toList());
    }

    public void updateFridgeName(FridgeState fridgeState) {
        FridgeStateEntity fridgeStateEntity = fridgeStateTransformer.transformToFridgeStateEntity(fridgeState);
        fridgeStateRepository.updateFridgeStateEntity(fridgeStateEntity);
        log.info("Updated fridgeState name with fridgeStateID=" + fridgeState.getFridgeStateID() + " and fridgeName=" + fridgeState.getFridgeName());
    }

    public void deleteFridgeStateByID(int fridgeStateID) {
        if(checkIfStateIsActual(fridgeStateID)){
            throw new InputException("Cannot delete actual fridgeState.");
        }
        fridgeStateRepository.deleteAllFridgeConnections(fridgeStateID);
        log.info("Deleted productFridgeStateEntity with fridgeStateID=" + fridgeStateID);
        fridgeStateRepository.deleteFridgeStateEntity(fridgeStateID);
        log.info("Deleted fridgeState with id=" + fridgeStateID);
    }

    private boolean checkIfStateIsActual(int fridgeStateID) {
        return fridgeStateRepository.getFridgeIsActual(fridgeStateID)
                .orElseThrow(() -> new InputException("There is no fridgeState with id=" + fridgeStateID));
    }
}
