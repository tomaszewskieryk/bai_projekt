package com.example.myshopper.service;

import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.Product;
import com.example.myshopper.model.enums.Unit;
import com.example.myshopper.repository.ProductRepository;
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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<CountedProduct> getCountedProductsByStateID(int fridgeStateID) {
        List<ProductStateEntity> productStateEntities = productRepository.getProductStateEntitiesByStateID(fridgeStateID);
        if (productStateEntities.isEmpty()) {
            log.info("Could not find any product_fridge state entities for stateID=" + fridgeStateID);
            return Collections.emptyList();
        }
        Map<String, Integer> productsAmountMap = getProductsAmountMap(productStateEntities);
        List<Integer> productIDs = getProductIds(productStateEntities);

        List<ProductEntity> productEntities = productRepository.getProductsByIds(productIDs);
        List<Product> products = transformToProducts(productEntities);
        return mergeProductsWithItsAmounts(products, productsAmountMap);
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

    public void updateProduct(CountedProduct product) {

    }

    public void createProductForFridgeState(int fridgeStateID, CountedProduct product) {
        ProductEntity productEntity = transformToProductEntity(product);
        int productID = productRepository.saveNewProductEntity(productEntity);

        ProductStateEntity productStateEntity = ProductStateEntity.builder()
                .fridgeStateID(fridgeStateID)
                .productID(productID)
                .amount(product.getAmount())
                .build();

        productRepository.saveNewProductStateEntity(productStateEntity);
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
}
