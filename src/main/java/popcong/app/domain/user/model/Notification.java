package popcong.app.domain.user.model;

import java.time.LocalDateTime;

public record Notification(
        Long notificationId,
        Long userId,
        NotificationType notificationType,
        String message,
        String url,
        boolean isRead,
        LocalDateTime createdAt
) {
}
