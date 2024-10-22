package com.quantus.backend.services.system;

import com.quantus.backend.models.system.Category;
import com.quantus.backend.repositories.system.CategoryRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.DateAndTimeUtils;
import com.quantus.backend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@Service
@RequiredArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public final SysIdSequenceService sysIdSequenceService;

    public List<Category> findAllCategories() {
        return categoryRepository.getAll();
    }

    public Category findCategoryById(Integer categoryId) {
        Category category = categoryRepository.getOne(categoryId);
        if(category == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The category cannot be found");
        }
        return category;
    }

    public Category createCategory(Category newCategory) {

        if(StringUtils.IsStringEmptyOrNull(newCategory.getName())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Category name is not valid.");
        }

        //If there is no matching names, then we can create the Location
        if (!checkIfCategoryNameExists(newCategory.getName())) {
            newCategory.setCategoryId(
                    sysIdSequenceService.getNextSysIdSequence(DateAndTimeUtils.getCurrentDate(), "C"));
            return categoryRepository.save(newCategory);
        }
        //Otherwise throw an error.
        throw new CustomExceptionHandler.BadRequestCustomException("Category name already exists.");
    }

    public Category updateCategory(String name, String description, Integer categoryId) {
        Category category = findCategoryById(categoryId);
        if(StringUtils.IsStringEmptyOrNull(name)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Category name cannot be empty.");
        }
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer categoryId) {
        Category category = findCategoryById(categoryId);
        categoryRepository.logicalDelete(category);
    }

    public Boolean checkIfCategoryNameExists(String name) {
        return categoryRepository.findAll().stream().anyMatch(
                existingCategory -> existingCategory.getName().equalsIgnoreCase(name));
    }
}