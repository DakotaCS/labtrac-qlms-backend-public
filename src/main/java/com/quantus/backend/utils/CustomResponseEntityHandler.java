package com.quantus.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */
public class CustomResponseEntityHandler extends ResponseEntity<Object> {

    public CustomResponseEntityHandler(HttpStatus status, String message) {
        super(message, status);
    }
}
