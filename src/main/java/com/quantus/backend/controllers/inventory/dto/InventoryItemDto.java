package com.quantus.backend.controllers.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quantus.backend.controllers.system.dto.CategoryDto;
import com.quantus.backend.controllers.system.dto.LocationDto;
import lombok.Data;
import java.util.Date;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@Data
public class InventoryItemDto {
    private Integer id;
    private String inventoryItemId;
    private String name;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date importDate;
    private LocationDto location;
    private CategoryDto category;
    private String status;
    private String casNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expirationDate;
    private Double originalQuantityAmount;
    private Double currentQuantityAmount;
    private String quantityUnit;
    private InventoryItemNotificationDto inventoryItemNotification;
}