package com.quantus.backend.services.system;

import com.quantus.backend.models.system.MenuItem;
import com.quantus.backend.models.system.MenuRole;
import com.quantus.backend.repositories.system.MenuRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-07
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRoleRepository menuRoleRepository;

    public List<MenuItem> findAllMenuItemsByUserId(Integer userId) {
        return menuRoleRepository.findAllByUserId(userId).stream()
                .map(MenuRole::getMenuItem)
                .collect(Collectors.toList());
    }
}
