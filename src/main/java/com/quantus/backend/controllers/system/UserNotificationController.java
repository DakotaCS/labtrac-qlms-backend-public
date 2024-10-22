package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.UserNotificationPatchRequest;
import com.quantus.backend.controllers.system.dto.UserNotificationDto;
import com.quantus.backend.services.system.UserNotificationService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-25
 *
 * Note: Users are auto-assigned default notifications upon user creation, and
 * notifications are auto-deleted on user deletion. The only requirement is editing
 * user notifications or retrieving them.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/user/notification")
public class UserNotificationController {

    private final UserNotificationService userNotificationService;

    /**
     * Retrieve all User Notifications
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllUserNotifications() {
        List<UserNotificationDto> userNotificationDtoList =
                userNotificationService.findAllUserNotifications().stream()
                .map(userNotification -> {
                    UserNotificationDto dto = new UserNotificationDto();
                    dto.setId(userNotification.getId());
                    dto.setUserName(userNotification.getUser().getUserName());
                    dto.setInventoryLowQuantityNotification(userNotification.getInventoryLowQuantityNotification());
                    dto.setInventoryExpiryDateNotification(userNotification.getInventoryExpiryDateNotification());
                    return dto;}).collect(Collectors.toList());
        return ResponseEntity.ok(userNotificationDtoList);
    }

    /**
     * Update a User Notification
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateUserNotificationBYId(
            @PathVariable(name = "id") Integer userNotificationId,
            @RequestBody UserNotificationPatchRequest userNotificationPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(userNotificationService.updateUserNotification(
                userNotificationPatchRequest, userNotificationId), UserNotificationDto.class));
    }


}
