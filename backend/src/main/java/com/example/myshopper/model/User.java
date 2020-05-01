package com.example.myshopper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userID;
    private String nickname;
    private String email;
    private String password;
    private List<FridgeState> fridgeStateList;
}
