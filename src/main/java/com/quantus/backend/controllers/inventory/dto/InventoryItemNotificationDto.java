package com.quantus.backend.controllers.inventory.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */

@Data
public class InventoryItemNotificationDto {
    private Integer id;
    private Integer itemId;
    private Boolean lowQuantityAlarm;
}
