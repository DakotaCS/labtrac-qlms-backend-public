package com.quantus.backend.controllers.system;

import com.quantus.backend.config.auth.AuthUserDetailsService;
import com.quantus.backend.controllers.system.dto.*;
import com.quantus.backend.services.system.MenuService;
import com.quantus.backend.services.system.UserRoleService;
import com.quantus.backend.services.system.UserService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-09
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/user")
public class UserController {

    private final MenuService menuService;
    private final UserService userService;
    private final UserRoleService userRoleService;

    @GetMapping("/current-user")
    public ResponseEntity<Object>  getCurrentUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetailsService.UserPrincipal userDetails =
                (AuthUserDetailsService.UserPrincipal) authentication.getPrincipal();

        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userDetails.getUserId());

        return ResponseEntity.ok(userDetailsDto);
    }

    /**
     * Retrieve all Users
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllUsers() {
        List<UserDetailsDto> users = DozerEntityMapper.mapObjectList(
                        userService.findAllUsers(), UserDetailsDto.class)
                .stream()
                .peek(user -> user.setUserRole(userRoleService.findUserRolesByUserId(user.getId())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Create a User
     * Constraints: Username must be unique. The user is assigned only one role. Access to menu items are
     * auto-assigned based on the role given in the DTO.
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserDto newUserDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                userService.createUser(newUserDto.getUserName(), newUserDto.getPassword(), newUserDto.getRole()),
                UserDetailsDto.class));
    }

    /**
     * Update Username
     */
    @PatchMapping(value = "/{id}/username")
    public ResponseEntity<Object> updateUsername(
            @PathVariable(name = "id") Integer userId,
            @RequestBody UpdateUserPatchRequest userUpdatePatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                userService.updateUsername(userUpdatePatchRequest.getUserName(), userId), UserDetailsDto.class));
    }

    /**
     * Update Password
     */
    @PatchMapping(value = "/{id}/password")
    public ResponseEntity<Object> updatePassword(
            @PathVariable(name = "id") Integer userId,
            @RequestBody UpdateUserPatchRequest userUpdatePatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                userService.updatePassword(userUpdatePatchRequest.getPassword(), userId), UserDetailsDto.class));
    }

    /**
     * Update User Role
     * Note: This will update the role of the user, and in turn change their menu item access
     */
    @PatchMapping(value = "/{id}/user-role")
    public ResponseEntity<Object> updateRoles(
            @PathVariable(name = "id") Integer userId,
            @RequestBody UpdateUserPatchRequest userUpdatePatchRequest) {
        userService.updateRoles(userUpdatePatchRequest.getRole(), userId);
        return ResponseEntity.ok("");
    }

    /**
     * Disable User. Sets a user as disabled so they can no longer login.
     */
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<Object> setUserStatus(
            @PathVariable(name = "id") Integer userId,
            @RequestBody UpdateUserPatchRequest userUpdatePatchRequest) {
        userService.setUserStatus(userUpdatePatchRequest.getUserStatus(), userId);
        return ResponseEntity.ok("");
    }

    /**
     * Delete a User. Permanently deletes all associated menu item roles. Sets User role as deleted.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(
            @PathVariable(name = "id") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("");
    }

    /**
     * Retrieve all Menu Items for a User
     */
    @GetMapping(value = "/{id}/menu-items")
    public ResponseEntity<Object> findAllMenuItemsByUserId(
            @PathVariable(name = "id") Integer userId) {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                menuService.findAllMenuItemsByUserId(userId), MenuItemDto.class));
    }
}
