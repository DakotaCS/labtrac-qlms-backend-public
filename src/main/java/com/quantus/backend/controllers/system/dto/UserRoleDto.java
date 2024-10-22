package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-03
 */

@Data
public class UserRoleDto {
    private Integer userId;
    private String roleName;
}
