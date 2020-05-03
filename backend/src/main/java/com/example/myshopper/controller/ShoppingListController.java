package com.example.myshopper.controller;

import com.example.myshopper.model.ShoppingList;
import com.example.myshopper.model.User;
import com.example.myshopper.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping("/fridges/{fridgeStateID}/generatelist")
    public int createNewShoppingList(@PathVariable int fridgeStateID) {
        return shoppingListService.createNewShoppingListForFridge(fridgeStateID);
    }

    @GetMapping("/users/{userID}/lists")
    public List<ShoppingList> getShoppingListsForUser(@PathVariable int userID) {
        return shoppingListService.getShoppingListsForUser(userID);
    }

    @GetMapping("/lists/{shoppingListID}")
    public ShoppingList getShoppingListByID(@PathVariable int shoppingListID) {
        return shoppingListService.getShoppingListByID(shoppingListID);
    }

    @DeleteMapping("/lists/{shoppingListID}")
    public void deleteShoppingListByID(@PathVariable int shoppingListID) {
        shoppingListService.deleteShoppingList(shoppingListID);
    }

    @PostMapping("/lists/{shoppingListID}/share")
    public int shareShoppingList(@PathVariable int shoppingListID, @RequestBody User user){
        return shoppingListService.shareShoppingList(shoppingListID, user.getEmail());
    }
}
