package com.example.myshopper.controller;

import com.example.myshopper.model.CountedProduct;
import com.example.myshopper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/fridges/{fridgeStateID}/products")
    public void addProductToFridgeState(@PathVariable int fridgeStateID, @RequestBody CountedProduct product) {
        productService.createProductForFridgeState(fridgeStateID, product);
    }

    @PutMapping("/fridges/{fridgeStateID}/products")
    public void updateProduct(@PathVariable int fridgeStateID, @RequestBody CountedProduct product) {
        productService.updateProduct(product, fridgeStateID);
    }

    @DeleteMapping("/fridges/{fridgeStateID}/products/{productID}")
    public void deleteProductFromFridgeState(@PathVariable int fridgeStateID, @PathVariable int productID) {
        productService.deleteProductFromFridge(fridgeStateID, productID);
    }

    @DeleteMapping("/lists/{shoppingListID}/products/{productID}")
    public void deleteProductFromShoppingList(@PathVariable int shoppingListID, @PathVariable int productID) {
        productService.deleteProductFromShoppingList(shoppingListID, productID);
    }
}
