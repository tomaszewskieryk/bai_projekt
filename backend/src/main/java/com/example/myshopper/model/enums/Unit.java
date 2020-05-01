package com.example.myshopper.model.enums;

import lombok.Getter;

@Getter
public enum Unit {

    KILOGRAM("kg"),
    DEKAGRAM("dg"),
    GRAM("g"),
    LITRE("l"),
    MILLILITRE("ml"),
    PIECE("p");

    private final String shortcut;

    Unit(String shortcut) {
        this.shortcut = shortcut;
    }
}
