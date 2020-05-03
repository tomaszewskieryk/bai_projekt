package com.example.myshopper.service;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.Product;
import com.example.myshopper.repository.FridgeStateRepository;
import com.example.myshopper.repository.ProductRepository;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.repository.model.ProductEntity;
import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ProductStateEntity;
import com.example.myshopper.transformer.ProductTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FridgeStateRepository fridgeStateRepository;
    private final ProductTransformer productTransformer;

    @Autowired
    public ProductService(ProductRepository productRepository, FridgeStateRepository fridgeStateRepository, ProductTransformer productTransformer) {
        this.productRepository = productRepository;
        this.fridgeStateRepository = fridgeStateRepository;
        this.productTransformer = productTransformer;
    }

    public List<CountedProduct> getCountedProductsByFridgeStateID(int fridgeStateID) {
        List<ProductStateEntity> productStateEntities = productRepository.getProductStateEntitiesByStateID(fridgeStateID);
        if (productStateEntities.isEmpty()) {
            log.info("Could not find any productFridgeStateEntities for fridgeStateID=" + fridgeStateID);
            return Collections.emptyList();
        }
        Map<String, Double> productsAmountMap = getProductsAmountInFridgeStateMap(productStateEntities);
        List<Integer> productIDs = getProductIdsFromProductStateEntities(productStateEntities);

        List<ProductEntity> productEntities = productRepository.getProductListByIDs(productIDs);
        List<Product> products = productTransformer.transformToProducts(productEntities);
        return mergeProductsWithItsAmounts(products, productsAmountMap);
    }

    public void createProductForFridgeState(int fridgeStateID, CountedProduct product) {
        int productID = product.getProductID();
        if (productID <= 0) {
            ProductEntity productEntity = productTransformer.transformToProductEntity(product);
            productID = productRepository.createProductEntity(productEntity);

            if (!checkIfStateIsActual(fridgeStateID)) {
                saveProductStateEntity(getActualFridgeStateID(fridgeStateID), productID, 0);
            }
        }
        saveProductStateEntity(fridgeStateID, productID, product.getAmount());
    }

    public void updateProduct(CountedProduct product, int fridgeStateID) {
        ProductEntity productEntity = productTransformer.transformToProductEntity(product);
        productRepository.updateProductEntity(productEntity);
        ProductStateEntity productStateEntity = productTransformer.transformToProductStateEntity(fridgeStateID, product);

        productRepository.updateProductStateEntity(productStateEntity);
    }

    public List<CountedProduct> getCountedProductsByShoppingListID(int shoppingListID) {
        List<ProductShoppingListEntity> productShoppingListEntities = productRepository.getProductShoppingListEntitiesByListID(shoppingListID);
        if (productShoppingListEntities.isEmpty()) {
            log.info("Could not find any productShoppingListEntities for shoppingListID=" + shoppingListID);
            return Collections.emptyList();
        }
        Map<String, Double> productsAmountMap = getProductsAmountInShoppingListMap(productShoppingListEntities);
        List<Integer> productIDs = getProductIdsFromProductShoppingListEntities(productShoppingListEntities);

        List<ProductEntity> productEntities = productRepository.getProductListByIDs(productIDs);
        List<Product> products = productTransformer.transformToProducts(productEntities);
        return mergeProductsWithItsAmounts(products, productsAmountMap);
    }

    private List<Integer> getProductIdsFromProductShoppingListEntities(List<ProductShoppingListEntity> productShoppingListEntities) {
        return productShoppingListEntities.stream()
                .map(ProductShoppingListEntity::getProductID)
                .collect(Collectors.toList());
    }

    private Map<String, Double> getProductsAmountInShoppingListMap(List<ProductShoppingListEntity> productShoppingListEntities) {
        Map<String, Double> productsAmountMap = new HashMap<>();
        productShoppingListEntities
                .forEach(productShoppingListEntity -> insertProductAmountToMap(productsAmountMap, productShoppingListEntity));
        return productsAmountMap;
    }

    private void saveProductStateEntity(int fridgeStateID, int productID, double amount) {
        ProductStateEntity productActualStateEntity = productTransformer.transformToProductStateEntity(fridgeStateID, productID, amount);

        productRepository.saveNewProductStateEntity(productActualStateEntity);
    }

    private int getActualFridgeStateID(int fridgeStateID) {
        return getAllUsersFridgeStatesEntities(fridgeStateID)
                .stream()
                .filter(FridgeStateEntity::isActual)
                .collect(Collectors.toList()).get(0).getFridgeStateID();
    }

    private List<FridgeStateEntity> getAllUsersFridgeStatesEntities(int fridgeStateID) {
        return fridgeStateRepository.getFridgeStatesEntitiesByUserID(
                fridgeStateRepository.getFridgeStateEntityByID(fridgeStateID).orElseThrow(
                        () -> new InputException("There is no fridge state with id=" + fridgeStateID)
                ).getUserID()
        );
    }

    private boolean checkIfStateIsActual(int fridgeStateID) {
        return fridgeStateRepository.getFridgeIsActual(fridgeStateID)
                .orElseThrow(() -> new InputException("There is no fridge state with id=" + fridgeStateID));
    }

    private Map<String, Double> getProductsAmountInFridgeStateMap(List<ProductStateEntity> productStateEntities) {
        Map<String, Double> productsAmountMap = new HashMap<>();
        productStateEntities
                .forEach(productStateEntity -> insertProductAmountToMap(productsAmountMap, productStateEntity));
        return productsAmountMap;
    }

    private void insertProductAmountToMap(Map<String, Double> productsAmountMap, ProductStateEntity productStateEntity) {
        productsAmountMap.put(String.valueOf(productStateEntity.getProductID()), productStateEntity.getAmount());
    }

    private void insertProductAmountToMap(Map<String, Double> productsAmountMap, ProductShoppingListEntity productStateEntity) {
        productsAmountMap.put(String.valueOf(productStateEntity.getProductID()), productStateEntity.getAmount());
    }

    private List<Integer> getProductIdsFromProductStateEntities(List<ProductStateEntity> productStateEntities) {
        return productStateEntities.stream()
                .map(ProductStateEntity::getProductID)
                .collect(Collectors.toList());
    }

    private List<CountedProduct> mergeProductsWithItsAmounts(List<Product> products, Map<String, Double> productsAmountMap) {
        return products.stream()
                .map(product -> createCountedProduct(productsAmountMap, product))
                .collect(Collectors.toList());
    }

    private CountedProduct createCountedProduct(Map<String, Double> productsAmountMap, Product product) {
        return new CountedProduct(product, productsAmountMap.get(String.valueOf(product.getProductID())));
    }


    public void deleteProductFromFridge(int fridgeStateID, int productID) {
        if (checkIfStateIsActual(fridgeStateID)) {
            deleteProductAndAllConnections(productID);
        } else {
            deleteFridgeProductConnection(fridgeStateID, productID);
        }
    }

    private void deleteProductAndAllConnections(int productID) {
        productRepository.deleteAllProductStateEntities(productID);
        productRepository.deleteAllProductShoppingListEntities(productID);
        productRepository.deleteProductEntity(productID);

        log.info("Deleted product with id=" + productID);
    }

    private void deleteFridgeProductConnection(int fridgeStateID, int productID) {
        productRepository.deleteProductStateEntity(fridgeStateID, productID);
        log.info("Deleted productStateEntity with productID=" + productID + " and fridgeStateID=" + fridgeStateID);
    }

    public void deleteProductFromShoppingList(int shoppingListID, int productID) {
        productRepository.deleteProductShoppingListEntity(shoppingListID, productID);
        log.info("Deleted productShoppingListEntity with productID=" + productID + " and shoppingListID=" + shoppingListID);
    }
}
