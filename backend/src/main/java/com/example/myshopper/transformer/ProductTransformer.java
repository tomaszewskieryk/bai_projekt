package com.example.myshopper.transformer;

import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.Product;
import com.example.myshopper.model.enums.Unit;
import com.example.myshopper.repository.model.ProductEntity;
import com.example.myshopper.repository.model.ProductStateEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTransformer {

    public List<Product> transformToProducts(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::transformToProduct)
                .collect(Collectors.toList());
    }

    public Product transformToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productID(productEntity.getProductID())
                .productName(productEntity.getProductName())
                .price(productEntity.getPrice())
                .unit(Unit.valueOf(productEntity.getUnit()))
                .build();
    }
    
    public ProductStateEntity transformToProductStateEntity(int fridgeStateID, int productID, double amount) {
        return ProductStateEntity.builder()
                .fridgeStateID(fridgeStateID)
                .productID(productID)
                .amount(amount)
                .build();
    }

    public ProductStateEntity transformToProductStateEntity(int fridgeStateID, CountedProduct product) {
        return ProductStateEntity.builder()
                .fridgeStateID(fridgeStateID)
                .productID(product.getProductID())
                .amount(product.getAmount())
                .build();
    }

    public ProductEntity transformToProductEntity(CountedProduct product) {
        return ProductEntity.builder()
                .productID(product.getProductID())
                .price(product.getPrice())
                .productName(product.getProductName())
                .unit(product.getUnit().toString())
                .build();
    }
}
