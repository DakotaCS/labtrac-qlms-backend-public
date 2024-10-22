package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.UserRole;
import com.quantus.backend.repositories.GeneralRepository;

public interface UserRoleRepository extends GeneralRepository<UserRole, Integer> {
    UserRole findUserRoleByUserId(Integer userId);
}
