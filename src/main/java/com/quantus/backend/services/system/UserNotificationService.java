package com.quantus.backend.services.system;

import com.quantus.backend.controllers.system.dto.UserNotificationPatchRequest;
import com.quantus.backend.models.system.User;
import com.quantus.backend.models.system.UserNotification;
import com.quantus.backend.repositories.system.UserNotificationRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-25
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;

    public List<UserNotification> findAllUserNotifications() {
        return userNotificationRepository.findAll();
    }

    public List<UserNotification> findAllUserNotificationsByLowQuantityNotificationIsTrue() {
        return userNotificationRepository.findAllByInventoryLowQuantityNotificationIsTrue();
    }

    public UserNotification findUserNotificationById(Integer id) {return userNotificationRepository.findById(id).get();}
    /**
     *
     * @param user
     * @return UserNotification
     *
     * Creates a User Notification with defaults
     */
    public UserNotification addUserNotification(User user) {
        UserNotification userNotification = new UserNotification();
        userNotification.setUser(user);
        userNotification.setInventoryLowQuantityNotification(false);
        userNotification.setInventoryExpiryDateNotification(false);
        return userNotificationRepository.save(userNotification);
    }

    public UserNotification updateUserNotification(
            UserNotificationPatchRequest userNotificationPatchRequest, Integer userNotificationId) {
        UserNotification userNotification = findUserNotificationById(userNotificationId);
        if (userNotification == null) {
            throw new CustomExceptionHandler.BadRequestCustomException("The user notification cannot be found");
        }
        userNotification.setInventoryLowQuantityNotification(
                userNotificationPatchRequest.getInventoryLowQuantityNotification());
        userNotification.setInventoryExpiryDateNotification(
                userNotificationPatchRequest.getInventoryExpiryDateNotification());
        return userNotificationRepository.save(userNotification);
    }

    public void removeUserNotification(User user) {
        if(userNotificationRepository.findFirstByUser(user) == null) {
            throw new CustomExceptionHandler.BadRequestCustomException("The user notification cannot be deleted");
        }
        userNotificationRepository.delete(userNotificationRepository.findFirstByUser(user));
    }
}
