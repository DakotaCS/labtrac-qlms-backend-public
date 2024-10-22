package com.quantus.backend.config.auth;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
