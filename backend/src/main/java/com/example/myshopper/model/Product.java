package com.example.myshopper.model;

import com.example.myshopper.model.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private int productID;
    private String productName;
    private Unit unit;
    private BigDecimal price;
}
