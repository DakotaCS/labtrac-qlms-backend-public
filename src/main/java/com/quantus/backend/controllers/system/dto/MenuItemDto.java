package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */

@Data
public class MenuItemDto {
    private Integer id;
    private String name;
    private String url;
    private String iconName;
    private Integer orderIndex;
    private Integer subOrderIndex;
}
