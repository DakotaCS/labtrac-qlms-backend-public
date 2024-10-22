package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.MenuRole;
import com.quantus.backend.repositories.GeneralRepository;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */
public interface MenuRoleRepository extends GeneralRepository<MenuRole, Integer> {
    List<MenuRole> findAllByUserId(Integer userId);
}
