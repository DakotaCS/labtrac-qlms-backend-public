package com.quantus.backend.services.system;

import com.quantus.backend.models.system.User;
import com.quantus.backend.models.system.UserRole;
import com.quantus.backend.repositories.system.UserRoleRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.UserRoleTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-02
 */

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    /**
     *
     * @param user
     * @param userRole
     * @return UserRole
     *
     * Note: Checks if a user Role is valid prior to creating the record. Does not check User ID validity.
     */
    public UserRole createUserRole(User user, String userRole) {
        if(!userRole.equals(UserRoleTypes.ADMIN.toString()) && !userRole.equals(UserRoleTypes.MANAGER.toString())
                && !userRole.equals(UserRoleTypes.TECH.toString())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Invalid user role");
        }

        UserRole newRole = new UserRole();
        newRole.setRoleName(userRole);
        newRole.setUser(user);
        return userRoleRepository.save(newRole);
    }

    /**
     *
     * @param userId
     * Note: Does not check User ID validity.
     */
    public void removeUserRole(Integer userId) {
        UserRole userRole = userRoleRepository.findUserRoleByUserId(userId);
        userRoleRepository.logicalDelete(userRole);
    }

    public void adjustUserRole(Integer userId, String newRole) {
        if(!newRole.equals(UserRoleTypes.ADMIN.toString()) && !newRole.equals(UserRoleTypes.MANAGER.toString())
                && !newRole.equals(UserRoleTypes.TECH.toString())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Invalid user role");
        }
        UserRole userRole = userRoleRepository.findUserRoleByUserId(userId);
        userRole.setRoleName(newRole);
        userRoleRepository.save(userRole);
    }

    public String findUserRolesByUserId(Integer userId) {
        UserRole userRole = userRoleRepository.findUserRoleByUserId(userId);
        return userRole.getRoleName();
    }
}