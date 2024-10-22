package com.quantus.backend.models.inventory;

import com.quantus.backend.models.GeneralModel;
import com.quantus.backend.models.system.Category;
import com.quantus.backend.models.system.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "item_type")
@Table(name = "inventory_item")
public class InventoryItem extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "inventory_item_id")
    protected String inventoryItemId;

    protected String name;

    @Column(name = "import_date")
    protected Date importDate;

    protected String status;

    @OneToOne
    @JoinColumn(name = "location_id")
    protected Location location;

    @OneToOne
    @JoinColumn(name = "category_id")
    protected Category category;

    @Column(name = "expire_date")
    protected Date expirationDate;

    @Column(name = "cas_number")
    protected String casNumber;

    @Column(name = "original_quantity_amount")
    protected Double originalQuantityAmount;

    @Column(name = "current_quantity_amount")
    protected Double currentQuantityAmount;

    @Column(name = "quantity_unit")
    protected String quantityUnit;
}
