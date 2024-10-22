package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.InventoryItemNotificationPatchRequest;
import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.models.inventory.SolidInventoryItem;
import com.quantus.backend.services.inventory.InventoryItemNotificationService;
import com.quantus.backend.services.inventory.LiquidInventoryItemService;
import com.quantus.backend.services.inventory.SolidInventoryItemService;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.DozerEntityMapper;
import com.quantus.backend.utils.InventoryItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory")
public class InventoryItemController {

    private final LiquidInventoryItemService liquidInventoryItemService;
    private final SolidInventoryItemService solidInventoryItemService;
    private final InventoryItemNotificationService inventoryItemNotificationService;

    /**
     * Create an Inventory Item
     * Constraints: Type must be: SOLID or LIQUID.
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createInventoryItem(@RequestBody InventoryItemDto inventoryItemDto) {

        if(inventoryItemDto.getType()== InventoryItemType.LIQUID.toString()) {
            return ResponseEntity.ok(DozerEntityMapper.mapObject(liquidInventoryItemService.createInventoryItem(
                            DozerEntityMapper.mapObject(inventoryItemDto, LiquidInventoryItem.class),
                            inventoryItemDto.getType()), InventoryItemDto.class));
        }
        if(inventoryItemDto.getType() == InventoryItemType.SOLID.toString()) {
            return ResponseEntity.ok(DozerEntityMapper.mapObject(solidInventoryItemService.createInventoryItem(
                            DozerEntityMapper.mapObject(inventoryItemDto, SolidInventoryItem.class),
                            inventoryItemDto.getType()), InventoryItemDto.class));
        } else {
            throw new CustomExceptionHandler.BadRequestCustomException("An error occurred creating inventory item");
        }
    }

    /**
     * Update an Inventory Item's notification preferences
     */
    @PatchMapping(value = "/{id}/notification")
    public ResponseEntity<Object> updateInventoryItemNotification(
            @PathVariable(name = "id") Integer id,
            @RequestBody InventoryItemNotificationPatchRequest request) {
        inventoryItemNotificationService.updateLowQuantityNotification(
                id, request.getLowQuantityAlarm());
        return ResponseEntity.ok("");
    }

}
