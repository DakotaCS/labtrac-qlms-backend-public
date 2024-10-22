package com.quantus.backend.models.system;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */

@Getter
@Setter
@Entity
@Table(name = "sys_location")
public class Location extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;
    @Column(name = "location_id")
    protected String locationId;
    protected String name;
    protected String description;
}
