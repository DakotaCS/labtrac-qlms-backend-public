package com.quantus.backend.services.system;

import com.quantus.backend.models.system.User;
import com.quantus.backend.repositories.system.UserRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final MenuRoleService menuRoleService;
    private final UserNotificationService userNotificationService;

    public List<User> findAllUsers() {
        return userRepository.getAll();
    }

    public User findUserById(Integer userId) {
        User user = userRepository.getOne(userId);
        if(user == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The user cannot be found");
        }
        return user;
    }

    public User createUser(String username, String password, String role) {

        if(userRepository.findUserByUserName(username)!=null) {
         throw new CustomExceptionHandler.BadRequestCustomException("Username is already in use");
        }
        //Create the basic user object
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setUserPassword(passwordEncoder.encode(password));
        newUser.setIsDisabled(false);
        userRepository.save(newUser);

        //Assign a system-wide role to the user. Only 1 role is allowed per user.
        newUser.setUserRoles(Collections.singletonList(userRoleService.createUserRole(newUser, role)));

        //Assign page access based on role
        menuRoleService.assignMenuItemsToUser(role, newUser);

        //Setup default email notifications
        userNotificationService.addUserNotification(newUser);

        return newUser;
    }

    public User updateUsername(String userName, Integer userId) {
        User user = findUserById(userId);
        //If the username field is empty/null or the username already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(userName) || checkIfUserNameExists(userName)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Username is not valid.");
        }
        user.setUserName(userName);
        return userRepository.save(user);
    }

    public User updatePassword(String password, Integer userId) {
        User user = findUserById(userId);
        //if the password field is empty/null or the password is under 8 chars, throw an error
        if(StringUtils.IsStringEmptyOrNull(password) || password.length() < 8) {
            throw new CustomExceptionHandler.BadRequestCustomException("Password is not valid or not long enough.");
        }
        user.setUserPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    /**
     *
     * @param role
     * @param userId
     *
     */
    public void updateRoles(String role, Integer userId) {
        User user = findUserById(userId);
        //Adjust the user role
        userRoleService.adjustUserRole(userId, role);

        //Then adjust menu access based on the new role. First remove existing menu item access
        menuRoleService.removeMenuItemsFromUser(user);
        //Then re-assign all menu item access based on the new user role
        menuRoleService.assignMenuItemsToUser(role, user);
    }

    public void setUserStatus(String status, Integer userId) {
        User user = findUserById(userId);

        switch(status) {
            case "enable":
                user.setIsDisabled(false);
                break;
            case "disable":
                user.setIsDisabled(true);
                break;
            default:
                throw new CustomExceptionHandler.BadRequestCustomException("User status is not valid.");
        }
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        User user = findUserById(userId);
        user.setIsDeleted(true);
        userRoleService.removeUserRole(userId);
        menuRoleService.removeMenuItemsFromUser(user);
        userNotificationService.removeUserNotification(user);
        userRepository.save(user);
    }

    public Boolean checkIfUserNameExists(String name) {
        return userRepository.findAll().stream().anyMatch(
                existingLocation -> existingLocation.getUserName().equalsIgnoreCase(name));
    }
}
