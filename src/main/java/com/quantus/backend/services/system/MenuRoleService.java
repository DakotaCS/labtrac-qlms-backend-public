package com.quantus.backend.services.system;

import com.quantus.backend.models.system.MenuRole;
import com.quantus.backend.models.system.User;
import com.quantus.backend.repositories.system.MenuRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-02
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MenuRoleService {

    private final MenuRoleRepository menuRoleRepository;
    private final MenuItemService menuItemService;

    /**
     *
     * @param user
     * @param menuItemId
     * @return UserRole
     *
     * Note: Checks if a menu item ID is valid prior to creating the record. Does not check User ID validity.
     */
    public MenuRole createMenuRole(User user, Integer menuItemId) {
        MenuRole menuRole = new MenuRole();
        menuRole.setUser(user);
        menuRole.setMenuItem(menuItemService.findById(menuItemId));
        return menuRoleRepository.save(menuRole);
    }

    /**
     *
     * @param role
     * @param user
     * @return List<MenuRole>
     * Note: Does not check if User ID is valid.
     */
    public void assignMenuItemsToUser(String role, User user) {
        if(role.equals("ADMIN")) {
            createMenuRole(user,1);
            createMenuRole(user,2);
            createMenuRole(user,3);
            createMenuRole(user,4);
            createMenuRole(user,5);
            createMenuRole(user,6);
            createMenuRole(user,7);
        }
        if(role.equals("MANAGER")) {
            createMenuRole(user,1);
            createMenuRole(user,2);
            createMenuRole(user,3);
            createMenuRole(user,4);
            createMenuRole(user,5);
            createMenuRole(user,6);
            createMenuRole(user,7);
        }
        if(role.equals("TECH")) {
            createMenuRole(user,1);
            createMenuRole(user,2);
            createMenuRole(user,4);
            createMenuRole(user,5);
            createMenuRole(user,6);
            createMenuRole(user,7);
        }
    }

    /**
     *
     * @param user
     * Note: Because we do not need to keep a record of Menu Items, we can perform a delete (non logical)
     * on the item stream.
     */
    public void removeMenuItemsFromUser(User user) {
        List<MenuRole> menuRoles = menuRoleRepository.findAllByUserId(user.getId());
        menuRoles.stream().forEach(menuRole -> menuRoleRepository.delete(menuRole));
    }
}
