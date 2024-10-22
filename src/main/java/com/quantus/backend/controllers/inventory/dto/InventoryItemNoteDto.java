package com.quantus.backend.controllers.inventory.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@Data
public class InventoryItemNoteDto {
    private Integer id;
    private Integer inventoryItemId;
    private String content;
}
