package com.quantus.backend.repositories.inventory;

import com.quantus.backend.models.inventory.InventoryItemNote;
import com.quantus.backend.repositories.GeneralRepository;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */
public interface InventoryItemNoteRepository extends GeneralRepository<InventoryItemNote, Integer> {
    List<InventoryItemNote> findAllByInventoryItemIdAndIsDeletedFalseOrderByCreateTimeAsc(Integer inventoryItemId);
}
