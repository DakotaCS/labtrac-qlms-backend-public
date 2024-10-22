package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.SimpleInventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.UnclassifiedInventoryTransferRequest;
import com.quantus.backend.controllers.inventory.dto.UpdateInventoryItemPatchRequest;
import com.quantus.backend.models.inventory.UnclassifiedInventoryItem;
import com.quantus.backend.services.inventory.InventoryItemNotificationService;
import com.quantus.backend.services.inventory.UnclassifiedInventoryItemService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-24
 *
 * @implNote Unclassified inventory can only be added via the bulk import option.
 * Unclassified inventory items do not support: quantity changes or status changes.
 *
 * Notifications are disabled by default and are not editable until the item is moved
 * into classified inventory.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory/unclassified")
public class UnclassifiedInventoryItemController {

    private final UnclassifiedInventoryItemService unclassifiedInventoryItemService;
    private final InventoryItemNotificationService inventoryItemNotificationService;

    /**
     * Retrieve an Inventory Item by ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findInventoryItemById(@PathVariable(name = "id") Integer itemId) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                unclassifiedInventoryItemService.findInventoryItemById(itemId), InventoryItemDto.class));
    }

    /**
     * Retrieve all Inventory Items
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllInventoryItems() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                unclassifiedInventoryItemService.findAllInventoryItems(), SimpleInventoryItemDto.class));
    }

    /**
     * Retrieve all Inventory Items with pagination
     */
    @GetMapping("/pageable")
    public ResponseEntity<Object> findAllInventoryItems(
            @RequestParam(value = "searchColumn", required = false, defaultValue = "Inventory Item") String searchColumn,
            @RequestParam(value = "searchValue", required = false, defaultValue = "") String searchValue,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UnclassifiedInventoryItem> itemPage = unclassifiedInventoryItemService.findAllInventoryItems(
                searchColumn, searchValue, pageable);
        return ResponseEntity.ok(itemPage.map(item -> DozerEntityMapper.mapObject(item, SimpleInventoryItemDto.class)));
    }

    /**
     *
     * Create an Inventory Item
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createInventoryItem(@RequestBody InventoryItemDto inventoryItemDto) {
        InventoryItemDto inventoryItem = DozerEntityMapper.mapObject(
                unclassifiedInventoryItemService.createInventoryItem(DozerEntityMapper.mapObject(
                        inventoryItemDto, UnclassifiedInventoryItem.class),
                        inventoryItemDto.getType()), InventoryItemDto.class);
        inventoryItemNotificationService.createInventoryItemNotification(
                inventoryItem.getId(),false);
        return ResponseEntity.ok(inventoryItem);
    }

    /**
     * Update an Inventory Item
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateInventoryItem(
            @PathVariable(name = "id") Integer itemId,
            @RequestBody UpdateInventoryItemPatchRequest updateInventoryItemPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                unclassifiedInventoryItemService.updateInventoryItem(updateInventoryItemPatchRequest.getName(),
                        updateInventoryItemPatchRequest.getCasNumber(),
                        updateInventoryItemPatchRequest.getCategoryId(),
                        updateInventoryItemPatchRequest.getLocationId(), itemId), InventoryItemDto.class));
    }

    /**
     * Delete an Inventory Item
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteInventoryItem(
            @PathVariable(name = "id") Integer categoryId) {
        unclassifiedInventoryItemService.deleteInventoryItem(categoryId);
        return ResponseEntity.ok("");
    }

    /**
     * Transfer an unclassified inventory item to a solid or liquid inventory item
     */
    @PostMapping(value = "/{id}/transfer")
    public ResponseEntity<Object> transferUnclassifiedInventoryToClassifiedInventoryItem(
            @PathVariable(name = "id") Integer itemId,
            @RequestBody UnclassifiedInventoryTransferRequest unclassifiedInventoryTransferRequest) {
        unclassifiedInventoryItemService.transferItemToClassifiedInventoryItem(
                itemId, unclassifiedInventoryTransferRequest.getInventoryType());
        return ResponseEntity.ok("");
    }
}