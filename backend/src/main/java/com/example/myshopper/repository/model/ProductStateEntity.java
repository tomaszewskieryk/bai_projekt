package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStateEntity {

    private int productID;
    private int fridgeStateID;
    private int amount;
}
