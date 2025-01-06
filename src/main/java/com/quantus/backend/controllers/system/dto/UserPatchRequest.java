package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-02
 */

@Data
public class UserPatchRequest {
    protected String userName;
    protected String password;
    protected String role;
    protected String userStatus;
}
