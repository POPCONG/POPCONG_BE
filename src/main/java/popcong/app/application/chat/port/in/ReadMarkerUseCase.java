package popcong.app.application.chat.port.in;

import java.time.LocalDateTime;

public interface ReadMarkerUseCase {
    void markAsRead(Long chatId, Long userId, LocalDateTime lastSeenAt);

    long countMyUnread(Long chatId, Long userId);
}
