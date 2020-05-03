package com.example.myshopper.controller;

import com.example.myshopper.model.ShoppingList;
import com.example.myshopper.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping("/fridges/{fridgeStateID}/generatelist")
    public int createNewShoppingList(@PathVariable int fridgeStateID){
        return shoppingListService.createNewShoppingListForFridge(fridgeStateID);
    }

    @GetMapping("/users/{userID}/lists")
    public List<ShoppingList> getShoppingListsForUser(@PathVariable int userID){
        return shoppingListService.getShoppingListsForUser(userID);
    }
}
