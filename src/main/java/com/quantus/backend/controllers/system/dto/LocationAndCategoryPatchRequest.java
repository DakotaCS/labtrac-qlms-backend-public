package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 */

@Data
public class LocationAndCategoryPatchRequest {
    protected String name;
    protected String description;
}
