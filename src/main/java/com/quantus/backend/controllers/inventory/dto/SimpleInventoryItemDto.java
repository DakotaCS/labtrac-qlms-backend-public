package com.quantus.backend.controllers.inventory.dto;

import com.quantus.backend.controllers.system.dto.CategoryDto;
import com.quantus.backend.controllers.system.dto.LocationDto;
import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@Data
public class SimpleInventoryItemDto {
    private Integer id;
    private String inventoryItemId;
    private String name;
    private LocationDto location;
    private CategoryDto category;
    private String status;
    private Double currentQuantityAmount;
    private String quantityUnit;
    private String casNumber;
}