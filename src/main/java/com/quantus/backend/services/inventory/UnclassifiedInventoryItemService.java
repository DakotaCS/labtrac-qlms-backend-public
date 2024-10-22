package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.UnclassifiedInventoryItem;
import com.quantus.backend.repositories.inventory.UnclassifiedInventoryItemRepository;
import com.quantus.backend.repositories.inventory.UnclassifiedInventoryItemSpecification;
import com.quantus.backend.services.system.CategoryService;
import com.quantus.backend.services.system.LocationService;
import com.quantus.backend.services.system.SysIdSequenceService;
import com.quantus.backend.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-24
 */

@Service
@RequiredArgsConstructor
public class UnclassifiedInventoryItemService {

    private final SysIdSequenceService sysIdSequenceService;
    private final UnclassifiedInventoryItemRepository unclassifiedInventoryItemRepository;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final SolidInventoryItemService solidInventoryItemService;
    private final LiquidInventoryItemService liquidInventoryItemService;

    public List<UnclassifiedInventoryItem> findAllInventoryItems() {
        return unclassifiedInventoryItemRepository.getAll();
    }

    public UnclassifiedInventoryItem findInventoryItemById(Integer itemId) {
        UnclassifiedInventoryItem unclassifiedInventoryItem = unclassifiedInventoryItemRepository.getOne(itemId);
        if(unclassifiedInventoryItem == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The inventory item cannot be found");
        }
        return unclassifiedInventoryItem;
    }

    public Page<UnclassifiedInventoryItem> findAllInventoryItems(
            String searchColumn, String searchValue, Pageable pageable) {
        Specification<UnclassifiedInventoryItem> spec = Specification.where(
                new UnclassifiedInventoryItemSpecification(searchColumn, searchValue)
        ).and((root, query, cb) -> cb.equal(root.get("isDeleted"), false));
        return unclassifiedInventoryItemRepository.findAll(spec, pageable);
    }

    public UnclassifiedInventoryItem createInventoryItem(UnclassifiedInventoryItem newInventoryItem, String type) {
        if(StringUtils.IsStringEmptyOrNull(newInventoryItem.getName()) ||
                StringUtils.IsStringEmptyOrNull(type)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name or type is not valid.");
        }

        newInventoryItem.setInventoryItemId(
                sysIdSequenceService.getNextSysIdSequence(DateAndTimeUtils.getCurrentDate(), "LC"));
        //Auto-set the status to OK and quantity amounts to 0.00
        newInventoryItem.setStatus(InventoryItemStatusType.OK.toString());
        newInventoryItem.setCurrentQuantityAmount(0.00);
        newInventoryItem.setOriginalQuantityAmount(0.00);

        newInventoryItem.setImportDate(DateAndTimeUtils.getCurrentTimeAsDate());
        newInventoryItem.setExpirationDate(DateAndTimeUtils.addMonthsToCurrentDate(6));

        newInventoryItem = unclassifiedInventoryItemRepository.save(newInventoryItem);

        return newInventoryItem;
    }

    public UnclassifiedInventoryItem updateInventoryItem(
            String name, String casNumber, Integer categoryId, Integer locationId,Integer itemId) {
        UnclassifiedInventoryItem unclassifiedInventoryItem = findInventoryItemById(itemId);
        //If the name field is empty/null or the name already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(name) || StringUtils.IsStringEmptyOrNull(casNumber)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name or CAS number is not valid.");
        }
        unclassifiedInventoryItem.setCategory(categoryService.findCategoryById(categoryId));
        unclassifiedInventoryItem.setLocation(locationService.findLocationById(locationId));
        unclassifiedInventoryItem.setName(name);
        unclassifiedInventoryItem.setCasNumber(casNumber);
        return unclassifiedInventoryItemRepository.save(unclassifiedInventoryItem);
    }

    public void deleteInventoryItem(Integer itemId) {
        UnclassifiedInventoryItem unclassifiedInventoryItem = findInventoryItemById(itemId);
        unclassifiedInventoryItemRepository.logicalDelete(unclassifiedInventoryItem);
    }

    public void transferItemToClassifiedInventoryItem(Integer id, String inventoryType) {
        UnclassifiedInventoryItem unclassifiedInventoryItem = findInventoryItemById(id);
        if(inventoryType.equals(InventoryItemType.SOLID.name())) {
            solidInventoryItemService.createInventoryItemFromTransfer(unclassifiedInventoryItem);
        }
        else if(inventoryType.equals(InventoryItemType.LIQUID.name())) {
            liquidInventoryItemService.createInventoryItemFromTransfer(unclassifiedInventoryItem);
        } else {
            throw new CustomExceptionHandler.BadRequestCustomException("The inventory type is not supported.");
        }
        //Logically delete the unclassified inventory after a successful transfer
        deleteInventoryItem(unclassifiedInventoryItem.getId());
    }
}
