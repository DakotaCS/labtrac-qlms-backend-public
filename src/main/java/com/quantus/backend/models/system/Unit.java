package com.quantus.backend.models.system;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "type")
@Table(name = "sys_unit")
public class Unit extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "quantity_unit")
    protected String quantityUnit;

    @Column(name = "quantity_unit_code")
    protected String quantityUnitCode;
}
