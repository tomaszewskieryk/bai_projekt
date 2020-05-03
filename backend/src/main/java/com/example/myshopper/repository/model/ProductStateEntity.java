package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStateEntity {

    private int productID;
    private int fridgeStateID;
    private double amount;
}
