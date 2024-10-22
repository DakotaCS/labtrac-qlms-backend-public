package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.InventoryItemNotificationDto;
import com.quantus.backend.controllers.inventory.dto.InventoryItemNotificationPatchRequest;
import com.quantus.backend.services.inventory.InventoryItemNotificationService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory/notification")
public class InventoryItemNotificationController {

    private final InventoryItemNotificationService inventoryItemNotificationService;

    /**
     * Setup Inventory Item Notifications
     * Constraints: Only called when a classified inventory item is created
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createInventoryItemNotification(
            @RequestBody InventoryItemNotificationDto inventoryItemNotificationDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                inventoryItemNotificationService.createInventoryItemNotification(
                        inventoryItemNotificationDto.getItemId(), inventoryItemNotificationDto.getLowQuantityAlarm()),
                InventoryItemNotificationDto.class));
    }

    /**
     * Update a Notification by Inventory Item
     */
    @PatchMapping(value = "/item/{id}")
    public ResponseEntity<Object> updateNotificationStatusViaItemId(
            @PathVariable(name = "id") Integer itemId,
            @RequestBody InventoryItemNotificationPatchRequest inventoryItemNotificationPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(inventoryItemNotificationService.
                updateLowQuantityNotification(itemId, inventoryItemNotificationPatchRequest.getLowQuantityAlarm()),
                InventoryItemDto.class));
    }
}
