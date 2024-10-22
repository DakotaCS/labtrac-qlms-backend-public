package com.quantus.backend.models.inventory;

import jakarta.persistence.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-23
 */

@Entity
@Table(name = "inventory_item_liquid_properties")
public class LiquidInventoryItemProperties {

    @Id
    protected Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "item_id")
    protected LiquidInventoryItem liquidInventoryItem;
}
