package com.example.myshopper.service;

import com.example.myshopper.exception.InternalException;
import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.FridgeState;
import com.example.myshopper.repository.FridgeStateRepository;
import com.example.myshopper.repository.model.FridgeStateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FridgeStateService {

    private final FridgeStateRepository fridgeStateRepository;
    private final ProductService productService;

    @Autowired
    public FridgeStateService(FridgeStateRepository fridgeStateRepository, ProductService productService) {
        this.fridgeStateRepository = fridgeStateRepository;
        this.productService = productService;
    }

    public void createFridgeStateForNewUser(int userID) {
        FridgeStateEntity fridgeStateEntity = new FridgeStateEntity("actualState", true, userID);

        int fridgeStateID = fridgeStateRepository.saveFridgeStateForNewUser(fridgeStateEntity);
        log.info("Created fridge state with id = " + fridgeStateID);
    }

    public FridgeState getFridgeStateByID(int stateID) {
        FridgeStateEntity fridgeStateEntity = fridgeStateRepository.getFridgeStateEntityByID(stateID);
        if (fridgeStateEntity.getFridgeStateID() == 0) {
            log.info("Could not find any fridge state with stateID=" + stateID);
            return null;
        }
        return transformFromDb(fridgeStateEntity);
    }

    public FridgeState getActualFridgeStateByUserID(int userID) {
        FridgeStateEntity fridgeStateEntity = fridgeStateRepository.getActualFridgeStateEntityByUserID(userID);
        if (fridgeStateEntity.getFridgeStateID() == 0) {
            throw new InternalException("No actual fridge state for user with id=" + userID);
        }
        return transformFromDb(fridgeStateEntity);
    }

    private FridgeState transformFromDb(FridgeStateEntity fsEntity) {
        return FridgeState.builder()
                .fridgeStateID(fsEntity.getFridgeStateID())
                .fridgeName(fsEntity.getFridgeName())
                .isActual(fsEntity.isActual())
                .products(getCountedProducts(fsEntity.getFridgeStateID()))
                .build();
    }

    private List<CountedProduct> getCountedProducts(int fridgeStateID) {
        return productService.getCountedProductsByStateID(fridgeStateID);
    }
}
