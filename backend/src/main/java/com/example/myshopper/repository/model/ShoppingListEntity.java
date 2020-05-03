package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListEntity {

    private int shoppingListID;
    private String shoppingListName;
    private int userID;
}
