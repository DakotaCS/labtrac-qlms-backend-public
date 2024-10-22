package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-12
 */

@Data
public class ZebraPrinterRequest {

    private Integer itemId;
    private String inventoryItemId;
    private String name;
    private String location;
}
