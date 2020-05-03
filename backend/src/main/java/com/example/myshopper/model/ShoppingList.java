package com.example.myshopper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal price = calculatePrice();

    private BigDecimal calculatePrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CountedProduct product : products) {
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));
        }
        return total;
    }

    public ShoppingList(String shoppingListName, List<CountedProduct> products) {
        this.shoppingListName = shoppingListName;
        this.products = products;
    }

    public ShoppingList(ShoppingList shoppingList){
        this(shoppingList.getShoppingListName(), shoppingList.getProducts());
    }
}
