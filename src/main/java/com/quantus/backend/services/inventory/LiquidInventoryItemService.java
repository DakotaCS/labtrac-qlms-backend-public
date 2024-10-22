package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.LiquidInventoryItem;
import com.quantus.backend.repositories.inventory.LiquidInventoryItemRepository;
import com.quantus.backend.services.system.CategoryService;
import com.quantus.backend.services.system.LocationService;
import com.quantus.backend.services.system.SysIdSequenceService;
import com.quantus.backend.utils.*;
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
public class LiquidInventoryItemService {

    private final SysIdSequenceService sysIdSequenceService;
    private final LiquidInventoryItemRepository liquidInventoryItemRepository;
    private final SetInventoryItemStatusHelper setInventoryItemStatusHelper;
    private final LocationService locationService;
    private final CategoryService categoryService;

    public List<LiquidInventoryItem> findAllInventoryItems() {
        return liquidInventoryItemRepository.getAll();
    }

    public LiquidInventoryItem findInventoryItemyById(Integer itemId) {
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

    public LiquidInventoryItem updateInventoryItem(
            String name, String casNumber, Integer categoryId, Integer locationId ,Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = findInventoryItemyById(itemId);
        //If the name field is empty/null or the name already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(name) || StringUtils.IsStringEmptyOrNull(casNumber)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name or CAS number is not valid.");
        }

        liquidInventoryItem.setCategory(categoryService.findCategoryById(categoryId));
        liquidInventoryItem.setLocation(locationService.findLocationById(locationId));
        liquidInventoryItem.setName(name);
        liquidInventoryItem.setCasNumber(casNumber);
        return liquidInventoryItemRepository.save(liquidInventoryItem);
    }

    public LiquidInventoryItem updateInventoryItemQuantity(Double quantityUsed, Integer itemId) {
        LiquidInventoryItem liquidInventoryItem = findInventoryItemyById(itemId);
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
        LiquidInventoryItem liquidInventoryItem = findInventoryItemyById(itemId);
        liquidInventoryItemRepository.logicalDelete(liquidInventoryItem);
    }
}
