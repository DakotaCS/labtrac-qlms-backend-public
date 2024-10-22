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
@Table(name = "sys_menu_role")
public class MenuRole extends GeneralModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    protected MenuItem menuItem;
}
