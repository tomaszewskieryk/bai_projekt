package com.example.myshopper.exception;

import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
    }
}
