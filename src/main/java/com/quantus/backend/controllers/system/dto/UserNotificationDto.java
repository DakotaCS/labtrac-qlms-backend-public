package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-25
 */

@Data
public class UserNotificationDto {
    private Integer id;
    private String userName;
    private Boolean inventoryLowQuantityNotification;
    private Boolean inventoryExpiryDateNotification;
}