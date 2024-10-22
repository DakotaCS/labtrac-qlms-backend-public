package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.User;
import com.quantus.backend.repositories.GeneralRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */
public interface UserRepository extends GeneralRepository<User, Integer> {
    User findUserByUserName(String username);
}