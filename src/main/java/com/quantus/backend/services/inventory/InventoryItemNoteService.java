package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.InventoryItemNote;
import com.quantus.backend.repositories.inventory.InventoryItemNoteRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

@Service
@RequiredArgsConstructor
public class InventoryItemNoteService {

    private final InventoryItemNoteRepository inventoryItemNoteRepository;

    public InventoryItemNote findById(Integer noteId) {
        InventoryItemNote inventoryItemNote = inventoryItemNoteRepository.getOne(noteId);
        if(inventoryItemNote == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The note cannot be found");
        }
        return inventoryItemNote;
    }

    public List<InventoryItemNote> findAllByInventoryItemId(Integer inventoryItemId) {
        return inventoryItemNoteRepository.
                findAllByInventoryItemIdAndIsDeletedFalseOrderByCreateTimeAsc(inventoryItemId);
    }

    public InventoryItemNote createNote(String content, Integer inventoryItemId) {
        InventoryItemNote note = new InventoryItemNote();
        note.setInventoryItemId(inventoryItemId);
        note.setContent(content);
        return inventoryItemNoteRepository.save(note);
    }

    public InventoryItemNote editNote(String content, Integer noteId) {

        InventoryItemNote note = findById(noteId);

        if(StringUtils.IsStringEmptyOrNull(content)) {
            throw new CustomExceptionHandler.BadRequestCustomException("No content to update");
        }
        note.setContent(content);
        return inventoryItemNoteRepository.save(note);
    }

    public void deleteNote(Integer inventoryItemNoteId) {
        inventoryItemNoteRepository.logicalDelete(findById(inventoryItemNoteId));
    }
}
