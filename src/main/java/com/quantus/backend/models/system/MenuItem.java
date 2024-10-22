package com.quantus.backend.models.system;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-07
 */

@Getter
@Setter
@Entity
@Table(name = "sys_menu_item")
public class MenuItem extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;
    protected String name;
    protected String url;
    @Column(name = "icon_name")
    protected String iconName;
    @Column(name = "order_index")
    protected Integer orderIndex;
    @Column(name = "sub_order_index")
    protected Integer subOrderIndex;

}
