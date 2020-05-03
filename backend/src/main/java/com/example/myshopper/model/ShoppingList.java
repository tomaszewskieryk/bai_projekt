package com.example.myshopper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingList {

    private int shoppingListID;
    private String shoppingListName;
    @Builder.Default
    List<CountedProduct> products = new ArrayList<>();

    public ShoppingList(String shoppingListName, List<CountedProduct> products) {
        this.shoppingListName = shoppingListName;
        this.products = products;
    }
}
