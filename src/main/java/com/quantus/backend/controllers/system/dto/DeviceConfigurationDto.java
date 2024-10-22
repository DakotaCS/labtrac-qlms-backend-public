package com.quantus.backend.controllers.system.dto;

import lombok.Data;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-19
 */

@Data
public class DeviceConfigurationDto {
    private String printerConfiguration;
    private String printerNetworkIp;
    private Integer printerNetworkPort;
}
