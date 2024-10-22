package com.quantus.backend.models.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-27
 */
@Getter
@Setter
@Entity
@Table(name = "inventory_item_notification")
public class InventoryItemNotification {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "item_id")
    protected Integer itemId;

    @Column(name = "low_quantity_alarm")
    private Boolean lowQuantityAlarm;

    @Column(name = "low_quantity_alarm_triggered")
    private Boolean lowQuantityAlarmTriggered;
}
