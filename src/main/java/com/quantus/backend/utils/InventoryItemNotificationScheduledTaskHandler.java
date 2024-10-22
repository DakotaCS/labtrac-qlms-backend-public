package com.quantus.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */
@Component
@RequiredArgsConstructor
public class InventoryItemNotificationScheduledTaskHandler {

    private final InventoryItemNotificationHandler inventoryItemNotificationHandler;

    @Scheduled(cron = "${spring.mail.notification-schedule}")
    public void schedule() {inventoryItemNotificationHandler.sendEndOfDayLowQuantityEmailNotifications();}
}
