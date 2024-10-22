package com.quantus.backend.repositories.inventory;

import com.quantus.backend.models.inventory.LiquidInventoryItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-24
 */
public class LiquidInventoryItemSpecification implements Specification<LiquidInventoryItem> {

    private final String column;
    private final String value;

    public LiquidInventoryItemSpecification(String column, String value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<LiquidInventoryItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate notDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);

        if (value == null || value.isEmpty()) {
            return notDeletedPredicate;
        }

        Predicate searchPredicate;

        switch (column) {
            case "Inventory Item":
                searchPredicate = criteriaBuilder.like(root.get("inventoryItemId"), "%" + value + "%");
                break;
            case "Name":
                searchPredicate = criteriaBuilder.like(root.get("name"), "%" + value + "%");
                break;
            case "Location Name":
                searchPredicate = criteriaBuilder.like(root.get("location").get("name"), "%" + value + "%");
                break;
            case "Category Name":
                searchPredicate = criteriaBuilder.like(root.get("category").get("name"), "%" + value + "%");
                break;
            case "Status":
                searchPredicate = criteriaBuilder.like(root.get("status"), "%" + value + "%");
                break;
            case "Current Quantity":
                searchPredicate = criteriaBuilder.equal(root.get("currentQuantityAmount"), Double.valueOf(value));
                break;
            case "Unit":
                searchPredicate = criteriaBuilder.like(root.get("quantityUnit"), "%" + value + "%");
                break;
            default:
                searchPredicate = criteriaBuilder.conjunction(); // No additional condition
                break;
        }

        return criteriaBuilder.and(notDeletedPredicate, searchPredicate);
    }
}