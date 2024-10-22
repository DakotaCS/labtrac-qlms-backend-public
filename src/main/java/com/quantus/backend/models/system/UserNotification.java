package com.quantus.backend.models.system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-25
 */

@Getter
@Setter
@Entity
@Table(name = "sys_user_notification")
public class UserNotification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @Column(name = "inventory_low_quantity_notification")
    protected Boolean inventoryLowQuantityNotification;

    @Column(name = "inventory_expiry_date_notification")
    protected Boolean inventoryExpiryDateNotification;

}
