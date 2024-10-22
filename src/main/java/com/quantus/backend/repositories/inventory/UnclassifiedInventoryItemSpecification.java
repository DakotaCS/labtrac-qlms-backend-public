package com.quantus.backend.repositories.inventory;

import com.quantus.backend.models.inventory.UnclassifiedInventoryItem;
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
public class UnclassifiedInventoryItemSpecification implements Specification<UnclassifiedInventoryItem> {

    private final String column;
    private final String value;

    public UnclassifiedInventoryItemSpecification(String column, String value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(
            Root<UnclassifiedInventoryItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
            default:
                searchPredicate = criteriaBuilder.conjunction(); // No additional condition
                break;
        }

        return criteriaBuilder.and(notDeletedPredicate, searchPredicate);
    }
}