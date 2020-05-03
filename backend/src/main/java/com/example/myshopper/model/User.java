package com.example.myshopper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FridgeState> fridgeStateList = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ShoppingList> shoppingLists = new ArrayList<>();
}
