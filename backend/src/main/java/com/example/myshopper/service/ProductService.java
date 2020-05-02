package com.example.myshopper.service;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.Product;
import com.example.myshopper.model.enums.Unit;
import com.example.myshopper.repository.FridgeStateRepository;
import com.example.myshopper.repository.ProductRepository;
import com.example.myshopper.repository.model.FridgeStateEntity;
import com.example.myshopper.repository.model.ProductEntity;
import com.example.myshopper.repository.model.ProductStateEntity;
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

    @Autowired
    public ProductService(ProductRepository productRepository, FridgeStateRepository fridgeStateRepository) {
        this.productRepository = productRepository;
        this.fridgeStateRepository = fridgeStateRepository;
    }

    public List<CountedProduct> getCountedProductsByStateID(int fridgeStateID) {
        List<ProductStateEntity> productStateEntities = productRepository.getProductStateEntitiesByStateID(fridgeStateID);
        if (productStateEntities.isEmpty()) {
            log.info("Could not find any product_fridge state entities for stateID=" + fridgeStateID);
            return Collections.emptyList();
        }
        Map<String, Integer> productsAmountMap = getProductsAmountMap(productStateEntities);
        List<Integer> productIDs = getProductIds(productStateEntities);

        List<ProductEntity> productEntities = productRepository.getProductListByIDs(productIDs);
        List<Product> products = transformToProducts(productEntities);
        return mergeProductsWithItsAmounts(products, productsAmountMap);
    }

    public void createProductForFridgeState(int fridgeStateID, CountedProduct product) {
        ProductEntity productEntity = transformToProductEntity(product);
        int productID = productRepository.createProductEntity(productEntity);

        saveProductStateEntity(fridgeStateID, productID, product.getAmount());

        if (!checkIfStateIsActual(fridgeStateID)) {
            saveProductStateEntity(getActualFridgeStateID(fridgeStateID), productID, 0);
        }
    }

    public void updateProduct(CountedProduct product, int fridgeStateID) {
        ProductEntity productEntity = transformToProductEntity(product);
        productRepository.updateProductEntity(productEntity);

        ProductStateEntity productStateEntity = transformToProductStateEntity(fridgeStateID, product);

        productRepository.updateProductStateEntity(productStateEntity);
    }

    private void saveProductStateEntity(int fridgeStateID, int productID, int amount) {
        ProductStateEntity productActualStateEntity = transformToProductStateEntity(fridgeStateID, productID, amount);

        productRepository.saveNewProductStateEntity(productActualStateEntity);
    }

    private ProductStateEntity transformToProductStateEntity(int fridgeStateID, int productID, int amount) {
        return ProductStateEntity.builder()
                .fridgeStateID(fridgeStateID)
                .productID(productID)
                .amount(amount)
                .build();
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

    private List<Product> transformToProducts(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::transformToProduct)
                .collect(Collectors.toList());
    }

    private Product transformToProduct(ProductEntity pe) {
        return Product.builder()
                .productID(pe.getProductID())
                .productName(pe.getProductName())
                .price(pe.getPrice())
                .unit(Unit.valueOf(pe.getUnit()))
                .build();
    }

    private Map<String, Integer> getProductsAmountMap(List<ProductStateEntity> productStateEntities) {
        Map<String, Integer> productsAmountMap = new HashMap<>();
        productStateEntities
                .forEach(productStateEntity -> insertProductAmountToMap(productsAmountMap, productStateEntity));
        return productsAmountMap;
    }

    private void insertProductAmountToMap(Map<String, Integer> productsAmountMap, ProductStateEntity productStateEntity) {
        productsAmountMap.put(String.valueOf(productStateEntity.getProductID()), productStateEntity.getAmount());
    }

    private List<Integer> getProductIds(List<ProductStateEntity> productStateEntities) {
        return productStateEntities.stream()
                .map(ProductStateEntity::getProductID)
                .collect(Collectors.toList());
    }

    private List<CountedProduct> mergeProductsWithItsAmounts(List<Product> products, Map<String, Integer> productsAmountMap) {
        return products.stream()
                .map(product -> createCountedProduct(productsAmountMap, product))
                .collect(Collectors.toList());
    }

    private CountedProduct createCountedProduct(Map<String, Integer> productsAmountMap, Product product) {
        return new CountedProduct(product, productsAmountMap.get(String.valueOf(product.getProductID())));
    }

    private ProductEntity transformToProductEntity(CountedProduct product) {
        return ProductEntity.builder()
                .productID(product.getProductID())
                .price(product.getPrice())
                .productName(product.getProductName())
                .unit(product.getUnit().toString())
                .build();
    }

    private ProductStateEntity transformToProductStateEntity(int fridgeStateID, CountedProduct product) {
        return ProductStateEntity.builder()
                .fridgeStateID(fridgeStateID)
                .productID(product.getProductID())
                .amount(product.getAmount())
                .build();
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
        productRepository.deleteProductEntity(productID);

        log.info("Deleted product with id=" + productID);
    }

    private void deleteFridgeProductConnection(int fridgeStateID, int productID) {
        productRepository.deleteProductStateEntity(fridgeStateID, productID);
        log.info("Deleted product_state entity with productID=" + productID + " and fridgeStateID=" + fridgeStateID);
    }
}
