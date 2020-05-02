package com.example.myshopper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data()
@AllArgsConstructor
@NoArgsConstructor
public class CountedProduct extends Product{

    int amount;

    public CountedProduct(Product product, int amount) {
        super(product.getProductID(), product.getProductName(), product.getUnit(), product.getPrice());
        this.amount = amount;
    }
}
