package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.*;
import com.quantus.backend.services.system.GlobalVariableService;
import com.quantus.backend.services.system.ZebraPrinterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-12
 *
 * QLMS provides 2 printer modes: local (USB print) or network (IP print).
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/print")
public class ZebraPrinterController {

    private final ZebraPrinterService zebraPrinterService;
    private final GlobalVariableService globalVariableService;

    /**
     *
     * @param printRequest
     * @return ZebraPrinterResponse
     *
     * Note: Printing locally from the browser is handled only by the front-end. ZPL generation happens on the
     * backend.
     */
    @PostMapping("/item")
    public ResponseEntity<Object> printItemLabel(@RequestBody ZebraPrinterRequest printRequest) {

        ZebraPrinterResponse response = new ZebraPrinterResponse();

        //Retrieve the system printer configuration
        String printerConfig = globalVariableService.findGlobalVariableValueByCode("PrinterConfig");

        //If the configuration is set to local, printing logic is contained on the frontend
        if(printerConfig.equals("LOCAL")) {
            response.setZplString(zebraPrinterService.printLocalInventoryItemLabel(printRequest.getItemId(),
                    printRequest.getInventoryItemId(), printRequest.getName(), printRequest.getLocation()));
            response.setPrinterConfig("LOCAL");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        //If the configuration is set to network, the backend handles all the printing
        if (printerConfig.equals("NETWORK")) {
            response.setZplString(zebraPrinterService.printNetworkInventoryItemLabel(
                    globalVariableService.findGlobalVariableValueByCode("PrinterNetworkIP"),
                    Integer.valueOf(globalVariableService.findGlobalVariableValueByCode("PrinterNetworkPort")),
                    printRequest.getItemId(), printRequest.getInventoryItemId(), printRequest.getName(),
                    printRequest.getLocation()));
            response.setPrinterConfig("NETWORK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not print the label.");
    }

    /**
     * Update the default Printer Configuration
     */
    @PatchMapping(value = "/locale")
    public ResponseEntity<Object> updatePrinterConfiguration(
            @RequestBody DeviceConfigurationDto deviceConfigurationDto) {
        globalVariableService.updatePrinterLocale(deviceConfigurationDto.getPrinterConfiguration());
        return ResponseEntity.ok("");
    }

    /**
     * Update the default network printer IP
     */
    @PatchMapping(value = "/ip")
    public ResponseEntity<Object> updatePrinterIP(
            @RequestBody DeviceConfigurationDto deviceConfigurationDto) {
        globalVariableService.updatePrinterIp(deviceConfigurationDto.getPrinterNetworkIp());
        return ResponseEntity.ok("");
    }

    /**
     * Update the default network printer Port
     */
    @PatchMapping(value = "/port")
    public ResponseEntity<Object> updatePrinterPort(
            @RequestBody DeviceConfigurationDto deviceConfigurationDto) {
        globalVariableService.updatePrinterPort(deviceConfigurationDto.getPrinterNetworkPort());
        return ResponseEntity.ok("");
    }

    /**
     * Get printer configuration settings
     */
    @GetMapping(value = "/configuration")
    public ResponseEntity<Object> getPrinterConfiguration() {
        ZebraPrinterConfigurationDto zebraPrinterConfigurationDto = new ZebraPrinterConfigurationDto();
        zebraPrinterConfigurationDto.setPrinterConfig(
                globalVariableService.findGlobalVariableValueByCode("PrinterConfig"));
        zebraPrinterConfigurationDto.setPrinterNetworkIp(
                globalVariableService.findGlobalVariableValueByCode("PrinterNetworkIP"));
        zebraPrinterConfigurationDto.setPrinterNetworkPort(
                globalVariableService.findGlobalVariableValueByCode("PrinterNetworkPort"));
        return ResponseEntity.ok(zebraPrinterConfigurationDto);
    }

    //POST /items Batch Print TBD
}
