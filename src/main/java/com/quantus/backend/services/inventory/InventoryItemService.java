package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.InventoryItem;
import com.quantus.backend.repositories.inventory.InventoryItemRepository;
import com.quantus.backend.repositories.inventory.LiquidInventoryItemRepository;
import com.quantus.backend.repositories.inventory.SolidInventoryItemRepository;
import com.quantus.backend.services.system.SysIdSequenceService;
import com.quantus.backend.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final LiquidInventoryItemRepository liquidInventoryItemRepository;
    private final SolidInventoryItemRepository solidInventoryItemRepository;
    public final SysIdSequenceService sysIdSequenceService;

    public List<InventoryItem> findInventoryItemsByIds(List<Integer> ids) {
        return inventoryItemRepository.findAllById(ids);
    }

    public String findInventoryItemType(Integer itemId) {
        if(liquidInventoryItemRepository.getOne(itemId) != null) {
            return InventoryItemType.LIQUID.toString();
        }
        if (solidInventoryItemRepository.getOne(itemId) != null) {
            return InventoryItemType.SOLID.toString();
        }
        else {
            throw new CustomExceptionHandler.BadRequestCustomException("The item cannot be found");
        }
    }

    /**
     *
     * @param locationId
     * @return InventoryItem
     * Note: Used by LocationController to check if any inventory is using the location. If so, it can't be deleted.
     */
    public InventoryItem findIfLocationIdIsBeingUsed(Integer locationId) {
        return inventoryItemRepository.findFirstByLocation_Id(locationId);
    }

    /**
     *
     * @param categoryId
     * @return InventoryItem
     * Note: Used by CategoryController to check if any inventory is using the category. If so, it can't be deleted.
     */
    public InventoryItem findIfCategoryIdIsBeingUsed(Integer categoryId) {
        return inventoryItemRepository.findFirstByCategory_Id(categoryId);
    }
}
