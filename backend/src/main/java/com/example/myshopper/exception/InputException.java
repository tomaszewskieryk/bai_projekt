package com.example.myshopper.exception;

import lombok.Getter;

@Getter
public class InputException extends RuntimeException {
    public InputException(String message) {
        super(message);
    }
}
