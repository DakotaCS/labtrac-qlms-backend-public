package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.InventoryItemNotification;
import com.quantus.backend.repositories.inventory.InventoryItemNotificationRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */

@Service
@RequiredArgsConstructor
public class InventoryItemNotificationService {

    private final InventoryItemNotificationRepository inventoryItemNotificationRepository;

    public InventoryItemNotification findByItemId(Integer id) {
        InventoryItemNotification inventoryItemNotification = inventoryItemNotificationRepository.findFirstByItemId(id);
        if(inventoryItemNotification == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("Inventory Item Notification not found");
        }
        return inventoryItemNotification;
    }

    public InventoryItemNotification createInventoryItemNotification(Integer itemId, Boolean lowQuantityNotification) {
        InventoryItemNotification inventoryItemNotification = new InventoryItemNotification();
        inventoryItemNotification.setLowQuantityAlarm(lowQuantityNotification);
        inventoryItemNotification.setLowQuantityAlarmTriggered(false);
        inventoryItemNotification.setItemId(itemId);
        return inventoryItemNotificationRepository.save(inventoryItemNotification);
    }

    /**
     * Retrieve all notification items where low quantity alarms are enabled,
     * but have not been previously triggered.
     * @return List<InventoryItemNotification
     */
    public List<InventoryItemNotification> findAllInventoryItemNotificationsWhereAlarmIsTrueAndTriggeredIsFalse() {
        return inventoryItemNotificationRepository.findAllByLowQuantityAlarmIsTrueAndLowQuantityAlarmTriggeredIsFalse();
    }

    /**
     * Set a low quantity alarm trigger to true to indicate the low quantity alarm
     * notification has been sent to the user.
     * @return List<InventoryItemNotification
     */
    public void setInventoryItemNotificationLowQuantityTriggerToTrue(List<InventoryItemNotification> notifications) {
        notifications.forEach(notification -> notification.setLowQuantityAlarmTriggered(true));
        inventoryItemNotificationRepository.saveAll(notifications);
    }

    /**
     * Set the status for the low quantity alarm. If the user disables the notification,
     * then reset the trigger value.
     * @return InventoryItemNotification
     */
    public InventoryItemNotification updateLowQuantityNotification(Integer itemId, Boolean status) {
        InventoryItemNotification inventoryItemNotification = findByItemId(itemId);
        if(status==false) {
            inventoryItemNotification.setLowQuantityAlarmTriggered(false);
        }
        inventoryItemNotification.setLowQuantityAlarm(status);
        return inventoryItemNotificationRepository.save(inventoryItemNotification);
    }

    public void deleteInventoryItemNotification(Integer itemId) {
        InventoryItemNotification inventoryItemNotification = findByItemId(itemId);
        inventoryItemNotificationRepository.delete(inventoryItemNotification);
    }
}
