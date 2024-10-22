package com.quantus.backend.models.system;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@Getter
@Setter
@Entity
@Table(name = "sys_category")
public class Category extends GeneralModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;
    @Column(name = "category_id")
    protected String categoryId;
    protected String name;
    protected String description;
}
