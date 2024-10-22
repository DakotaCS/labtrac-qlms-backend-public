package com.quantus.backend.repositories.inventory;

import com.quantus.backend.models.inventory.InventoryItemNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */
public interface InventoryItemNotificationRepository extends JpaRepository<InventoryItemNotification, Integer> {
    List<InventoryItemNotification> findAllByLowQuantityAlarmIsTrueAndLowQuantityAlarmTriggeredIsFalse();
    InventoryItemNotification findFirstByItemId(Integer itemId);
}