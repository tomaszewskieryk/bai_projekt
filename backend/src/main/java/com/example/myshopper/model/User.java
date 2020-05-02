package com.example.myshopper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userID;
    private String nickname;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<FridgeState> fridgeStateList = new ArrayList<>();
    private List<ShoppingList> shoppingLists = new ArrayList<>();
}
