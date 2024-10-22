package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.CategoryDto;
import com.quantus.backend.controllers.system.dto.LocationDto;
import com.quantus.backend.controllers.system.dto.LocationAndCategoryPatchRequest;
import com.quantus.backend.models.system.Category;
import com.quantus.backend.services.inventory.InventoryItemService;
import com.quantus.backend.services.system.CategoryService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final InventoryItemService inventoryItemService;

    /**
     * Retrieve all Categories
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllLocations() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                categoryService.findAllCategories(), CategoryDto.class));
    }

    /**
     * Retrieve a Category by ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findCategoryById(@PathVariable(name = "id") Integer locationId) {
        return ResponseEntity.ok(
                DozerEntityMapper.mapObject(categoryService.findCategoryById(locationId), CategoryDto.class));
    }

    /**
     * Create a Category
     * Constraints: Category names must be unique. Category IDs are auto-generated as C-20240101-X
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                categoryService.createCategory(DozerEntityMapper.mapObject(categoryDto, Category.class)
                ), CategoryDto.class));
    }

    /**
     * Update a Category
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable(name = "id") Integer locationId,
            @RequestBody LocationAndCategoryPatchRequest locationAndCategoryPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                categoryService.updateCategory(locationAndCategoryPatchRequest.getName(),
                        locationAndCategoryPatchRequest.getDescription(), locationId), LocationDto.class));
    }

    /**
     * Delete a Category
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteLocation(
            @PathVariable(name = "id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("");
    }
}
