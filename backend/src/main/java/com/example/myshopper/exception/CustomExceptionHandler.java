package com.example.myshopper.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Object> serverError(InternalException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InputException.class)
    public ResponseEntity<Object> badRequest(InputException e) {
        log.warn(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

//    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> unsuspected(RuntimeException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Object> notImplemented(NotImplementedException e) {
        return new ResponseEntity<>(
                "Work in progress...",
                HttpStatus.NOT_IMPLEMENTED
        );
    }
}
