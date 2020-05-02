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
public class FridgeState {

    private int fridgeStateID;
    private String fridgeName;
    private boolean isActual;
    private List<CountedProduct> products = new ArrayList<>();

    public FridgeState(String fridgeName, boolean isActual) {
        this.fridgeName = fridgeName;
        this.isActual = isActual;
    }
}
