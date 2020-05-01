package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeStateEntity {

    private int fridgeStateID;
    private String fridgeName;
    private boolean isActual;
    private int userID;

    public FridgeStateEntity(String fridgeName, boolean isActual, int userID) {
        this.fridgeName = fridgeName;
        this.isActual = isActual;
        this.userID = userID;
    }
}
