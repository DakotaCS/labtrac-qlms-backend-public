package com.quantus.backend.controllers.inventory.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

@Data
public class UpdateInventoryItemPatchRequest {
    private String name;
    private String casNumber;
    private Integer categoryId;
    private Integer locationId;
    private Double quantityUsed;
}
