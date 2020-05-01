package com.example.myshopper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeState {

    private int fridgeStateID;
    private String fridgeName;
    private boolean isActual;
    private List<CountedProduct> products;

}
