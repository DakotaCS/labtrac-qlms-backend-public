package com.quantus.backend.services.inventory;

import com.quantus.backend.models.inventory.SolidInventoryItem;
import com.quantus.backend.repositories.inventory.SolidInventoryItemRepository;
import com.quantus.backend.repositories.inventory.SolidInventoryItemSpecification;
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
public class SolidInventoryItemService {

    private final SysIdSequenceService sysIdSequenceService;
    private final SolidInventoryItemRepository solidInventoryItemRepository;
    private final SetInventoryItemStatusHelper setInventoryItemStatusHelper;
    private final LocationService locationService;
    private final CategoryService categoryService;

    public List<SolidInventoryItem> findAllInventoryItems() {
        return solidInventoryItemRepository.getAll();
    }

    public SolidInventoryItem findInventoryItemById(Integer itemId) {
        SolidInventoryItem solidInventoryItem = solidInventoryItemRepository.getOne(itemId);
        if(solidInventoryItem == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The inventory item cannot be found");
        }
        return solidInventoryItem;
    }

    public Page<SolidInventoryItem> findAllInventoryItems(String searchColumn, String searchValue, Pageable pageable) {
        Specification<SolidInventoryItem> spec = Specification.where(
                new SolidInventoryItemSpecification(searchColumn, searchValue)
        ).and((root, query, cb) -> cb.equal(root.get("isDeleted"), false));

        return solidInventoryItemRepository.findAll(spec, pageable);
    }

    public SolidInventoryItem createInventoryItem(SolidInventoryItem newInventoryItem, String type) {

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

        newInventoryItem = solidInventoryItemRepository.save(newInventoryItem);

        return newInventoryItem;
    }

    public SolidInventoryItem updateInventoryItem(
            String name, String casNumber, Integer categoryId, Integer locationId,Integer itemId) {
        SolidInventoryItem solidInventoryItem = findInventoryItemById(itemId);
        //If the name field is empty/null or the name already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(name) || StringUtils.IsStringEmptyOrNull(casNumber)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Item name or CAS number is not valid.");
        }
        solidInventoryItem.setCategory(categoryService.findCategoryById(categoryId));
        solidInventoryItem.setLocation(locationService.findLocationById(locationId));
        solidInventoryItem.setName(name);
        solidInventoryItem.setCasNumber(casNumber);
        return solidInventoryItemRepository.save(solidInventoryItem);
    }

    public SolidInventoryItem updateInventoryItemQuantity(Double quantityUsed, Integer itemId) {
        SolidInventoryItem solidInventoryItem = findInventoryItemById(itemId);
        if(quantityUsed > solidInventoryItem.getOriginalQuantityAmount()) {
            throw new CustomExceptionHandler.BadRequestCustomException("The current quantity amount cannot be the " +
                    "same or larger then the original quantity amount.");
        }
        if(quantityUsed < 0.00) {
            throw new CustomExceptionHandler.BadRequestCustomException(
                    "The current quantity amount cannot be negative.");
        }
        solidInventoryItem.setCurrentQuantityAmount(solidInventoryItem.getCurrentQuantityAmount()-quantityUsed);
        solidInventoryItemRepository.save(solidInventoryItem);
        setInventoryItemStatusHelper.recalculateSolidItemStatus(solidInventoryItem);
        return solidInventoryItem;
    }

    public void deleteInventoryItem(Integer itemId) {
        SolidInventoryItem solidInventoryItem = findInventoryItemById(itemId);
        solidInventoryItemRepository.logicalDelete(solidInventoryItem);
    }
}
