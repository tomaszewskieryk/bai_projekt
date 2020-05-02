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

    @PostMapping("/product/{fridgeStateID}")
    public void addProductToFridgeState(@PathVariable int fridgeStateID, @RequestBody CountedProduct product) {
        productService.createProductForFridgeState(fridgeStateID, product);
    }

    @PutMapping("/product}")
    public void updateProduct(@RequestBody CountedProduct product) {
        productService.updateProduct(product);
    }
}
