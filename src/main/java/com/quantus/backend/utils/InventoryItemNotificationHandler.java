package com.quantus.backend.utils;

import com.quantus.backend.models.inventory.InventoryItem;
import com.quantus.backend.models.inventory.InventoryItemNotification;
import com.quantus.backend.models.system.UserNotification;
import com.quantus.backend.services.inventory.InventoryItemNotificationService;
import com.quantus.backend.services.inventory.InventoryItemService;
import com.quantus.backend.services.system.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 *
 */

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryItemNotificationHandler {

    @Value("${spring.mail.template.low-quantity}") private String lowQuantityTemplate;

    private final InventoryItemNotificationService inventoryItemNotificationService;
    private final UserNotificationService userNotificationService;
    private final MailSenderHelper mailSenderHelper;
    private final InventoryItemService inventoryItemService;

    public void sendEndOfDayLowQuantityEmailNotifications() {
        List<UserNotification> usersToNotify =
                userNotificationService.findAllUserNotificationsByLowQuantityNotificationIsTrue();

        List<InventoryItemNotification> inventoryItemNotifications =
                inventoryItemNotificationService.findAllInventoryItemNotificationsWhereAlarmIsTrueAndTriggeredIsFalse();

        List<InventoryItem> inventoryItemsToSend = inventoryItemService.findInventoryItemsByIds(
                inventoryItemNotifications.stream().map(InventoryItemNotification::getItemId)
                        .collect(Collectors.toList()));

        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("items", inventoryItemsToSend);

        if(usersToNotify.size() > 0 && inventoryItemsToSend.size() > 0) {
            usersToNotify.stream().forEach(userNotification -> mailSenderHelper.sendEmail(
                    userNotification.getUser().getUserName(), "Low Quantity Notification Email",
                    emailVariables, lowQuantityTemplate));
        }

        inventoryItemNotificationService.setInventoryItemNotificationLowQuantityTriggerToTrue(inventoryItemNotifications);
    }
}
