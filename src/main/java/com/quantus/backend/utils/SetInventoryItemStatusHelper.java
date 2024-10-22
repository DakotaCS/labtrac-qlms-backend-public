package com.quantus.backend.utils;

import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.models.inventory.SolidInventoryItem;
import com.quantus.backend.repositories.inventory.LiquidInventoryItemRepository;
import com.quantus.backend.repositories.inventory.SolidInventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 *
 * Note: Sets the status based on the percentage of material left for the item.
 * Future development: E-mail notifications (if enabled for each item) will be handled here.
 *
 */

@Service
@Transactional
@RequiredArgsConstructor
public class SetInventoryItemStatusHelper {

    private final LiquidInventoryItemRepository liquidInventoryItemRepository;
    private final SolidInventoryItemRepository solidInventoryItemRepository;


    public void recalculateLiquidItemStatus(LiquidInventoryItem item) {
        Double currentQuantityRemaining = item.getOriginalQuantityAmount() - item.getCurrentQuantityAmount();
        Double percentageOfQuantityRemaining = currentQuantityRemaining / item.getOriginalQuantityAmount();
        //If the item has under 20% remaining, set the status as low
        if(percentageOfQuantityRemaining < 0.2) {
            item.setStatus(InventoryItemStatusType.LOW.toString());
            liquidInventoryItemRepository.save(item);
        }
        //If the item has nothing left, set it as depleted
        if(percentageOfQuantityRemaining == 0.00) {
            item.setStatus(InventoryItemStatusType.DEPLETED.toString());
            liquidInventoryItemRepository.save(item);
        }
    }

    public void recalculateSolidItemStatus(SolidInventoryItem item) {
        Double currentQuantityRemaining = item.getOriginalQuantityAmount() - item.getCurrentQuantityAmount();
        Double percentageOfQuantityRemaining = currentQuantityRemaining / item.getOriginalQuantityAmount();
        //If the item has under 20% remaining, set the status as low
        if(percentageOfQuantityRemaining < 0.2) {
            item.setStatus(InventoryItemStatusType.LOW.toString());
            solidInventoryItemRepository.save(item);
        }
        //If the item has nothing left, set it as depleted
        if(percentageOfQuantityRemaining == 0.00) {
            item.setStatus(InventoryItemStatusType.DEPLETED.toString());
            solidInventoryItemRepository.save(item);
        }
    }
}
