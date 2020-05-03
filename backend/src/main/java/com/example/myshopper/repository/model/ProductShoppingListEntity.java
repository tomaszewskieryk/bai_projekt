package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShoppingListEntity{

    private int productID;
    private int shoppingListID;
    private double amount;
}
