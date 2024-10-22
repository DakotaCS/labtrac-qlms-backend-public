package com.quantus.backend.services.system;

import com.quantus.backend.services.inventory.InventoryItemService;
import com.quantus.backend.utils.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-12
 */

@Service
@RequiredArgsConstructor
public class ZebraPrinterService {

    private final InventoryItemService inventoryItemService;

    public String printInventoryItemLabel(Integer itemId, String inventoryItemId, String name, String location) {
        String itemType = inventoryItemService.findInventoryItemType(itemId);
        return constructInventoryItemZPLString(itemType, itemId, inventoryItemId, name, location);
    }

    public String constructInventoryItemZPLString(
            String inventoryType, Integer itemId, String inventoryItemId, String name, String location) {
        return "^XA  \n" +
                "^FO10,5^BQN,6,6^FDLA,http://localhost:3000/inventory/" + inventoryType.toLowerCase() +
                "/" + itemId + "^FS\n" +
                "^FO200,30^A0N,25,25^FD" + inventoryItemId + "^FS\n" +
                "^FO200,65^A0N,15,15^FDItem Name:^FS\n" +
                "^FO220,100^A0N,15,15^FD" +  name + "^FS\n" +
                "^FO200,135^A0N,15,15^FDLocation Name:^FS\n" +
                "^FO220,170^A0N,15,15^FD" +  location + "^FS\n" +
                "^XZ";
    }

    public String printNetworkInventoryItemLabel(String printerNetworkIP, Integer port,
                                                 Integer itemId, String inventoryItemId, String name, String location) {
        String itemType = inventoryItemService.findInventoryItemType(itemId);
        String zpl = constructInventoryItemZPLString(itemType, itemId, inventoryItemId, name, location);

        try (Socket clientSocket = new Socket(printerNetworkIP, port);
             OutputStream out = clientSocket.getOutputStream()) {
            out.write(zpl.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new CustomExceptionHandler.BadRequestCustomException(
                    "The printer network IP address or port is invalid");
        }
        return zpl;
    }

}
