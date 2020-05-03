package com.example.myshopper.transformer;

import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.model.ShoppingList;
import com.example.myshopper.repository.model.ProductShoppingListEntity;
import com.example.myshopper.repository.model.ShoppingListEntity;
import com.example.myshopper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingListTransformer {

    private final ProductService productService;

    @Autowired
    public ShoppingListTransformer(ProductService productService) {
        this.productService = productService;
    }


    public ShoppingList transformToShoppingList(ShoppingListEntity shoppingListEntity) {
        return ShoppingList.builder()
                .shoppingListID(shoppingListEntity.getShoppingListID())
                .shoppingListName(shoppingListEntity.getShoppingListName())
                .products(getCountedProducts(shoppingListEntity.getShoppingListID()))
                .build();
    }

    public ShoppingListEntity transformToShoppingListEntity(ShoppingList shoppingList, int userID) {
        return ShoppingListEntity.builder()
                .shoppingListID(shoppingList.getShoppingListID())
                .shoppingListName(shoppingList.getShoppingListName())
                .userID(userID)
                .build();
    }

    public List<ProductShoppingListEntity> transformToProductShoppingListEntities(ShoppingList shoppingList) {
        List<ProductShoppingListEntity> productShoppingListEntities = new ArrayList<>();
        shoppingList.getProducts()
                .forEach(product ->
                        productShoppingListEntities.add(transformToProductShoppingListEntity(product, shoppingList.getShoppingListID()))
                );
        return productShoppingListEntities;
    }

    private ProductShoppingListEntity transformToProductShoppingListEntity(CountedProduct product, int shoppingListID) {
        return ProductShoppingListEntity.builder()
                .productID(product.getProductID())
                .shoppingListID(shoppingListID)
                .amount(product.getAmount())
                .build();
    }

    private List<CountedProduct> getCountedProducts(int shoppingListID) {
        return productService.getCountedProductsByShoppingListID(shoppingListID);
    }
}
