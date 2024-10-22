package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.User;
import com.quantus.backend.models.system.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-25
 */
public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    UserNotification findFirstByUser(User user);
    List<UserNotification> findAllByInventoryLowQuantityNotificationIsTrue();
}
