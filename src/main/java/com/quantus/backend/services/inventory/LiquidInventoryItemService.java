package com.quantus.backend.services.inventory;

import com.quantus.backend.controllers.inventory.dto.UpdateInventoryItemPatchRequest;
import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.models.inventory.UnclassifiedInventoryItem;
import com.quantus.backend.repositories.inventory.LiquidInventoryItemRepository;
import com.quantus.backend.repositories.inventory.LiquidInventoryItemSpecification;
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
 * @since 2024-10-08
 */

@Service
@RequiredArgsConstructor
public class LiquidInventoryItemService {

    private final SysIdSequenceService sysIdSequenceService;
    private final LiquidInventoryItemRepository liquidInventoryItemRepository;
    private final SetInventoryItemStatusHelper setInventoryItemStatusHelper;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final InventoryItemNotificationService inventoryItemNotificationService;

    public List<LiquidInventoryItem> findAllInventoryItems() {
        return liquidInventoryItemRepository.getAll();
    }

    public Page<LiquidInventoryItem> findAllInventoryItems(String searchColumn, String searchValue, Pageable pageable) {
        Specification<LiquidInventoryItem> spec = Specification.where(
                new LiquidInventoryItemSpecification(searchColumn, searchValue)
        ).and((root, query, cb) -> cb.equal(root.get("isDeleted"), false));

        return liquidInventoryItemRepository.findAll(spec, pageable);
    }

    public LiquidInventoryItem findInventoryItemById(Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = liquidInventoryItemRepository.getOne(itemId);
        if(liquidInventoryItem == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The inventory item cannot be found");
        }
        return liquidInventoryItem;
    }

    public LiquidInventoryItem createInventoryItem(LiquidInventoryItem newInventoryItem, String type) {

        if(StringUtils.IsStringEmptyOrNull(newInventoryItem.getName()) ||
                StringUtils.IsStringEmptyOrNull(type)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name or type is not valid.");
        }
        if(newInventoryItem.getOriginalQuantityAmount()<=0 ||
                StringUtils.IsStringEmptyOrNull(newInventoryItem.getQuantityUnit())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Quantity amount and unit is not valid.");
        }

        if(DateAndTimeUtils.isFirstDateBeforeThenSecondDate(DateAndTimeUtils.getCurrentTimeAsDate(),
                newInventoryItem.getExpirationDate())) {
            throw new CustomExceptionHandler.BadRequestCustomException("The expiry date is not valid.");
        }

        newInventoryItem.setInventoryItemId(
                sysIdSequenceService.getNextSysIdSequence(DateAndTimeUtils.getCurrentDate(), "LC"));
        newInventoryItem.setStatus(InventoryItemStatusType.OK.toString());
        newInventoryItem.setCurrentQuantityAmount(newInventoryItem.getOriginalQuantityAmount());

        newInventoryItem = liquidInventoryItemRepository.save(newInventoryItem);

        return newInventoryItem;
    }

    public LiquidInventoryItem createInventoryItemFromTransfer(UnclassifiedInventoryItem unclassifiedInventoryItem) {
        LiquidInventoryItem liquidInventoryItem = new LiquidInventoryItem();
        liquidInventoryItem.setInventoryItemId(unclassifiedInventoryItem.getInventoryItemId());
        liquidInventoryItem.setName(unclassifiedInventoryItem.getName());
        liquidInventoryItem.setImportDate(unclassifiedInventoryItem.getImportDate());
        liquidInventoryItem.setStatus(InventoryItemStatusType.OK.toString());
        liquidInventoryItem.setLocation(unclassifiedInventoryItem.getLocation());
        liquidInventoryItem.setCategory(unclassifiedInventoryItem.getCategory());
        liquidInventoryItem.setExpirationDate(unclassifiedInventoryItem.getExpirationDate());
        liquidInventoryItem.setCasNumber(unclassifiedInventoryItem.getCasNumber());
        //Editing quantity amounts for unclassified inventory isn't allowed
        liquidInventoryItem.setOriginalQuantityAmount(0.00);
        liquidInventoryItem.setCurrentQuantityAmount(0.00);
        //Default delineation to grams
        liquidInventoryItem.setQuantityUnit("ml");
        return liquidInventoryItemRepository.save(liquidInventoryItem);
    }

    public LiquidInventoryItem updateInventoryItem(UpdateInventoryItemPatchRequest
            updateInventoryItemPatchRequest, Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = findInventoryItemById(itemId);
        //If the name field is empty/null or the name already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(updateInventoryItemPatchRequest.getName())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name is not valid.");
        }

        liquidInventoryItem.setCategory(categoryService.findCategoryById(updateInventoryItemPatchRequest.getCategoryId()));
        liquidInventoryItem.setLocation(locationService.findLocationById(updateInventoryItemPatchRequest.getLocationId()));
        liquidInventoryItem.setName(updateInventoryItemPatchRequest.getName());
        liquidInventoryItem.setCasNumber(updateInventoryItemPatchRequest.getCasNumber());
        return liquidInventoryItemRepository.save(liquidInventoryItem);
    }

    public LiquidInventoryItem updateInventoryItemQuantity(Double quantityUsed, Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = findInventoryItemById(itemId);
        if(quantityUsed > liquidInventoryItem.getOriginalQuantityAmount()) {
            throw new CustomExceptionHandler.BadRequestCustomException("The current quantity amount cannot be the " +
                    "same or larger then the original quantity amount.");
        }
        if(quantityUsed < 0.00) {
            throw new CustomExceptionHandler.BadRequestCustomException(
                    "The current quantity amount cannot be negative.");
        }
        liquidInventoryItem.setCurrentQuantityAmount(liquidInventoryItem.getCurrentQuantityAmount()-quantityUsed);
        liquidInventoryItemRepository.save(liquidInventoryItem);
        setInventoryItemStatusHelper.recalculateLiquidItemStatus(liquidInventoryItem);
        return liquidInventoryItem;
    }

    public void deleteInventoryItem(Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = findInventoryItemById(itemId);
        inventoryItemNotificationService.deleteInventoryItemNotification(liquidInventoryItem.getId());
        liquidInventoryItemRepository.logicalDelete(liquidInventoryItem);
    }
}
