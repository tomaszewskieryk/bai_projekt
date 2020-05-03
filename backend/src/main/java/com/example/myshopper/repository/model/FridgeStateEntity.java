package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
