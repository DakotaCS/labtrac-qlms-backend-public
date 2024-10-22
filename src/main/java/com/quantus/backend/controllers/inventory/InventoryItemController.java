package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemDto;
import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.models.inventory.SolidInventoryItem;
import com.quantus.backend.services.inventory.InventoryItemService;
import com.quantus.backend.services.inventory.LiquidInventoryItemService;
import com.quantus.backend.services.inventory.SolidInventoryItemService;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.DozerEntityMapper;
import com.quantus.backend.utils.InventoryItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;
    private final LiquidInventoryItemService liquidInventoryItemService;
    private final SolidInventoryItemService solidInventoryItemService;

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
}
