package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.InventoryItemNotificationDto;
import com.quantus.backend.controllers.inventory.dto.SimpleInventoryItemDto;
import com.quantus.backend.controllers.inventory.dto.UpdateInventoryItemPatchRequest;
import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.services.inventory.InventoryItemNotificationService;
import com.quantus.backend.services.inventory.LiquidInventoryItemService;
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
 * @since 2024-10-08
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory/liquid")
public class LiquidInventoryItemController {

    private final LiquidInventoryItemService liquidInventoryItemService;
    private final InventoryItemNotificationService inventoryItemNotificationService;

    /**
     * Retrieve an Inventory Item by ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findInventoryItemById(@PathVariable(name = "id") Integer itemId) {
        InventoryItemDto inventoryItemDto = DozerEntityMapper.mapObject(
                liquidInventoryItemService.findInventoryItemById(itemId), InventoryItemDto.class);
        inventoryItemDto.setInventoryItemNotification(DozerEntityMapper.mapObject(
                inventoryItemNotificationService.findByItemId(itemId), InventoryItemNotificationDto.class));
        return ResponseEntity.ok(inventoryItemDto);
    }

    /**
     * Retrieve all Inventory Items
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllInventoryItems() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                liquidInventoryItemService.findAllInventoryItems(), SimpleInventoryItemDto.class));
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
        Page<LiquidInventoryItem> itemPage = liquidInventoryItemService.findAllInventoryItems(
                searchColumn, searchValue, pageable);
        return ResponseEntity.ok(itemPage.map(item -> DozerEntityMapper.mapObject(item, SimpleInventoryItemDto.class)));
    }

    /**
     *
     * Create an Inventory Item
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createInventoryItem(@RequestBody InventoryItemDto inventoryItemDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(liquidInventoryItemService.createInventoryItem(
                DozerEntityMapper.mapObject(inventoryItemDto, LiquidInventoryItem.class),
                inventoryItemDto.getType()), InventoryItemDto.class));
    }

    /**
     * Update an Inventory Item
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateInventoryItem(
            @PathVariable(name = "id") Integer itemId,
            @RequestBody UpdateInventoryItemPatchRequest updateInventoryItemPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(liquidInventoryItemService.updateInventoryItem(
                        updateInventoryItemPatchRequest, itemId), InventoryItemDto.class));
    }

    /**
     * Update an Inventory Item Quantity
     */
    @PatchMapping(value = "/{id}/quantity")
    public ResponseEntity<Object> updateInventoryItemQuantity(
            @PathVariable(name = "id") Integer itemId,
            @RequestBody UpdateInventoryItemPatchRequest updateInventoryItemPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                liquidInventoryItemService.updateInventoryItemQuantity(
                        updateInventoryItemPatchRequest.getQuantityUsed(), itemId),
                InventoryItemDto.class));
    }

    /**
     * Delete an Inventory Item
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteInventoryItem(
            @PathVariable(name = "id") Integer categoryId) {
        liquidInventoryItemService.deleteInventoryItem(categoryId);
        return ResponseEntity.ok("");
    }
}
