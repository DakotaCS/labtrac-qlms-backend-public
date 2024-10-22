package com.quantus.backend.config.auth;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

@Data
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
