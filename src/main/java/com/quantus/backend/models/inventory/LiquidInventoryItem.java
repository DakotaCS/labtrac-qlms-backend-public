package com.quantus.backend.models.inventory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */
@Entity
@DiscriminatorValue("LIQUID")
public class LiquidInventoryItem extends InventoryItem {}
