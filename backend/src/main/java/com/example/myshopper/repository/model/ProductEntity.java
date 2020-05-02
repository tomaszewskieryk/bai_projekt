package com.example.myshopper.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    private int productID;
    private String productName;
    private String unit;
    private BigDecimal price;
}
