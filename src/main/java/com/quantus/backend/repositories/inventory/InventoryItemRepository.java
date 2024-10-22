package com.quantus.backend.repositories.inventory;

import com.quantus.backend.models.inventory.InventoryItem;
import com.quantus.backend.repositories.GeneralRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */
public interface InventoryItemRepository extends GeneralRepository<InventoryItem, Integer> {
    InventoryItem findFirstByLocation_Id(Integer locationId);
    InventoryItem findFirstByCategory_Id(Integer categoryId);
}
