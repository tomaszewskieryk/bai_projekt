package com.example.myshopper.service;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.exception.InternalException;
import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.FridgeState;
import com.example.myshopper.model.ShoppingList;
import com.example.myshopper.model.User;
import com.example.myshopper.repository.FridgeStateRepository;
import com.example.myshopper.repository.ShoppingListRepository;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ShoppingListEntity;
import com.example.myshopper.transformer.FridgeStateTransformer;
import com.example.myshopper.transformer.ShoppingListTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    private final FridgeStateRepository fridgeStateRepository;
    private final FridgeStateService fridgeStateService;
    private final FridgeStateTransformer fridgeStateTransformer;
    private final ShoppingListTransformer shoppingListTransformer;
    private final UserService userService;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, FridgeStateRepository fridgeStateRepository, FridgeStateService fridgeStateService, FridgeStateTransformer fridgeStateTransformer, ShoppingListTransformer shoppingListTransformer, UserService userService) {
        this.shoppingListRepository = shoppingListRepository;
        this.fridgeStateRepository = fridgeStateRepository;
        this.fridgeStateService = fridgeStateService;
        this.fridgeStateTransformer = fridgeStateTransformer;
        this.shoppingListTransformer = shoppingListTransformer;
        this.userService = userService;
    }

    public int createNewShoppingListForFridge(int fridgeStateID) {
        FridgeStateEntity fridgeStateEntity = fridgeStateRepository.getFridgeStateEntityByID(fridgeStateID)
                .orElseThrow(() -> new InputException("Could not find any fridgeState with fridgeStateID=" + fridgeStateID));
        ShoppingList shoppingList = createShoppingList(fridgeStateEntity);

        if(!shoppingList.getProducts().isEmpty()) {
            ShoppingListEntity shoppingListEntity = createDatabaseShoppingList(shoppingList, fridgeStateEntity.getUserID());
            return shoppingListEntity.getShoppingListID();
        } else {
            throw new InputException("Shopping list empty");
        }
    }

    public int shareShoppingList(int shoppingListID, String email) {
        ShoppingListEntity originalShoppingListEntity = shoppingListRepository.getShoppingListEntitiesByID(shoppingListID)
                .orElseThrow(() -> new InputException("Could not find any shoppingList with shoppingListID=" + shoppingListID));
        ShoppingList originalShoppingList = shoppingListTransformer.transformToShoppingList(originalShoppingListEntity);
        User targetUser = userService.getUserByEmail(email);

        ShoppingList newShoppingList = new ShoppingList(originalShoppingList);
        ShoppingListEntity newShoppingListEntity = createDatabaseShoppingList(newShoppingList, targetUser.getUserID());

        return newShoppingListEntity.getShoppingListID();
    }

    public List<ShoppingList> getShoppingListsForUser(int userID) {
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.getShoppingListEntitiesByUserID(userID);

        return shoppingListEntities.stream()
                .map(shoppingListTransformer::transformToShoppingList)
                .collect(Collectors.toList());
    }

    public ShoppingList getShoppingListByID(int shoppingListID) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.getShoppingListEntitiesByID(shoppingListID)
                .orElseThrow(() -> new InputException("Could not find any shoppingList with shoppingListID=" + shoppingListID));
        return shoppingListTransformer.transformToShoppingList(shoppingListEntity);
    }

    public void deleteShoppingList(int shoppingListID) {
        shoppingListRepository.deleteAllShoppingListConnections(shoppingListID);
        log.info("Deleted productShoppingListEntity with shoppingListID=" + shoppingListID);
        shoppingListRepository.deleteShippingListEntity(shoppingListID);
        log.info("Deleted shoppingList with shoppingListID=" + shoppingListID);
    }

    private ShoppingListEntity createDatabaseShoppingList(ShoppingList shoppingList, int userID) {
        ShoppingListEntity shoppingListEntity = shoppingListTransformer.transformToShoppingListEntity(shoppingList, userID);
        int shoppingListID = shoppingListRepository.createShoppingListEntity(shoppingListEntity);
        log.info("Created shoppingList with id=" + shoppingListID);
        shoppingList.setShoppingListID(shoppingListID);

        List<ProductShoppingListEntity> productShoppingListEntities = shoppingListTransformer.transformToProductShoppingListEntities(shoppingList);
        shoppingListRepository.createProductShoppingListEntities(productShoppingListEntities);
        return shoppingListEntity;
    }

    private ShoppingList createShoppingList(FridgeStateEntity fridgeStateEntity) {
        FridgeState fridgeState = fridgeStateTransformer.transformToFridgeState(fridgeStateEntity);
        FridgeState actualFridgeState = fridgeStateService.getActualFridgeStateByUserID(fridgeStateEntity.getUserID());

        List<CountedProduct> missingProducts = findMissingProducts(fridgeState.getProducts(), actualFridgeState.getProducts());

        String shoppingListName = fridgeState.getFridgeName() + " lista zakupów";
        return new ShoppingList(shoppingListName, missingProducts);
    }

    private List<CountedProduct> findMissingProducts(List<CountedProduct> target, List<CountedProduct> current) {
        List<CountedProduct> needed = new ArrayList<>();
        Map<String, CountedProduct> currentProductsMap = createProductMap(current);

        target.forEach(targetProduct ->
                addMissingProductToList(needed, currentProductsMap, targetProduct)
        );
        return needed;
    }

    private void addMissingProductToList(List<CountedProduct> needed, Map<String, CountedProduct> currentProductsMap, CountedProduct targetProduct) {
        CountedProduct currentProduct = Optional.ofNullable(currentProductsMap.get(String.valueOf(targetProduct.getProductID())))
                .orElseThrow(() -> new InternalException("Error during comparing products: no target product in actual fridgeState"));

        double missingAmount = targetProduct.getAmount() - currentProduct.getAmount();
        if (missingAmount > 0) {
            needed.add(new CountedProduct(currentProduct, missingAmount));
        }
    }

    private Map<String, CountedProduct> createProductMap(List<CountedProduct> products) {
        Map<String, CountedProduct> currentProductsMap = new HashMap<>();
        products.forEach(product -> currentProductsMap.put(String.valueOf(product.getProductID()), product));
        return currentProductsMap;
    }
}
