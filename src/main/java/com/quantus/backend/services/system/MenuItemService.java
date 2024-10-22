package com.quantus.backend.services.system;

import com.quantus.backend.models.system.MenuItem;
import com.quantus.backend.repositories.system.MenuItemRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-02
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItem findById(int menuItemId) {
        MenuItem menuItem = menuItemRepository.getOne(menuItemId);
        if(menuItem == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The menu item cannot be found");
        }
        return menuItem;
    }
}
