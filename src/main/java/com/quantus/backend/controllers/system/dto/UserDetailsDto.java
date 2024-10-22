package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */

@Data
public class UserDetailsDto {
    private Integer id;
    private String userName;
    private Boolean isDisabled;
    private String userRole;
}
