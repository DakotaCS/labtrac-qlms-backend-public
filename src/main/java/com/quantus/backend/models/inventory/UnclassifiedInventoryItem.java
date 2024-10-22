package com.quantus.backend.models.inventory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-23
 *
 * Unclassified Items are items imported from existing inventory. They do not have:
 * 1. Status
 * 2. Quantity Information
 * 3. Quantity Unit
 *
 */
@Entity
@DiscriminatorValue("UNCLASSIFIED")
public class UnclassifiedInventoryItem extends InventoryItem {}
