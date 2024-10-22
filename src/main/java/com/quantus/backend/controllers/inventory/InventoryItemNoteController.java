package com.quantus.backend.controllers.inventory;

import com.quantus.backend.controllers.inventory.dto.InventoryItemNoteDto;
import com.quantus.backend.controllers.inventory.dto.UpdateInventoryItemNotePatchRequest;
import com.quantus.backend.services.inventory.InventoryItemNoteService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/inventory/note")
public class InventoryItemNoteController {

    private final InventoryItemNoteService inventoryItemNoteService;

    /**
     * Retrieve Notes by Inventory ID
     */
    @GetMapping(value = "/item/{id}")
    public ResponseEntity<Object> findAllByInventoryItemId(
            @PathVariable(name = "id") Integer inventoryItemId) {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                inventoryItemNoteService.findAllByInventoryItemId(inventoryItemId), InventoryItemNoteDto.class));
    }

    /**
     * Create an Inventory Note
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createInventoryItemNote(
            @RequestBody InventoryItemNoteDto inventoryItemNoteDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(inventoryItemNoteService.createNote(
                inventoryItemNoteDto.getContent(), inventoryItemNoteDto.getInventoryItemId()),
                InventoryItemNoteDto.class));
    }

    /**
     * Edit an Inventory Note
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> editInventoryItemNote(
            @PathVariable(name = "id") Integer inventoryItemNoteId,
            @RequestBody UpdateInventoryItemNotePatchRequest updateInventoryItemNotePatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(inventoryItemNoteService.editNote(
                updateInventoryItemNotePatchRequest.getContent(), inventoryItemNoteId), InventoryItemNoteDto.class));
    }

    /**
     * Delete an Inventory Note
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteInventoryItemNote(
            @PathVariable(name = "id") Integer inventoryItemNoteId) {
        inventoryItemNoteService.deleteNote(inventoryItemNoteId);
        return ResponseEntity.ok("");
    }
}