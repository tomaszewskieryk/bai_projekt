package com.example.myshopper.transformer;

import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.FridgeState;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeStateTransformer {

    private final ProductService productService;

    @Autowired
    public FridgeStateTransformer(ProductService productService) {
        this.productService = productService;
    }

    public FridgeStateEntity transformToFridgeStateEntity(FridgeState fridgeState) {
        return FridgeStateEntity.builder()
                .fridgeStateID(fridgeState.getFridgeStateID())
                .fridgeName(fridgeState.getFridgeName())
                .isActual(fridgeState.isActual())
                .build();
    }

    public FridgeState transformToSimpleFridgeState(FridgeStateEntity fridgeStateEntity) {
        return FridgeState.builder()
                .fridgeStateID(fridgeStateEntity.getFridgeStateID())
                .fridgeName(fridgeStateEntity.getFridgeName())
                .isActual(fridgeStateEntity.isActual())
                .build();
    }

    public FridgeState transformToFridgeState(FridgeStateEntity fridgeStateEntity) {
        return FridgeState.builder()
                .fridgeStateID(fridgeStateEntity.getFridgeStateID())
                .fridgeName(fridgeStateEntity.getFridgeName())
                .isActual(fridgeStateEntity.isActual())
                .products(getCountedProducts(fridgeStateEntity.getFridgeStateID()))
                .build();
    }

    private List<CountedProduct> getCountedProducts(int fridgeStateID) {
        return productService.getCountedProductsByFridgeStateID(fridgeStateID);
    }
}
