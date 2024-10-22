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
 * The back-end does not control printing, but will generate all ZPL label codes.
 * It also will store the default printer UID.
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
     * Note: All printing is carried out by the front-end via the Zebra BrowserPrint Lib
     */
    @PostMapping("/item")
    public ResponseEntity<Object> printItemLabel(@RequestBody ZebraPrinterRequest printRequest) {

        ZebraPrinterResponse response = new ZebraPrinterResponse();

        response.setZplString(zebraPrinterService.printInventoryItemLabel(printRequest.getItemId(),
                printRequest.getInventoryItemId(), printRequest.getName(), printRequest.getLocation()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get the default printer UID
     */
    @GetMapping(value = "/default-printer")
    public ResponseEntity<Object> getPrinterConfiguration() {
        ZebraPrinterConfigurationDto zebraPrinterConfigurationDto = new ZebraPrinterConfigurationDto();
        zebraPrinterConfigurationDto.setDefaultPrinterUid(
                globalVariableService.findGlobalVariableValueByCode("defaultPrinterUid"));
        return ResponseEntity.ok(zebraPrinterConfigurationDto);
    }

    /**
     * Update the default printer UID
     */
    @PatchMapping(value = "/default-printer")
    public ResponseEntity<Object> updateDefaultPrinter(
            @RequestBody ZebraPrinterConfigurationDto zebraPrinterConfigurationDto) {
        globalVariableService.updateDefaultPrinter(zebraPrinterConfigurationDto.getDefaultPrinterUid());
        return ResponseEntity.ok("");
    }
}
