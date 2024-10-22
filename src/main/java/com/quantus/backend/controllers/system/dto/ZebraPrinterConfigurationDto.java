package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-20
 */

@Data
public class ZebraPrinterConfigurationDto {
    private String printerConfig;
    private String printerNetworkIp;
    private String printerNetworkPort;
}
