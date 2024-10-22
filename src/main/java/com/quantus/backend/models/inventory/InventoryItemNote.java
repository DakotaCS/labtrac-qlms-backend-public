package com.quantus.backend.models.inventory;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-07
 */

@Getter
@Setter
@Entity
@Table(name = "inventory_item_note")
public class InventoryItemNote extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "inventory_item_id")
    protected Integer inventoryItemId;

    protected String content;
}
