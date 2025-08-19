package popcong.app.application.chat.dto.response;

import java.time.LocalDateTime;

public record ChatInfoDto(
        Long chatId,
        String lastMessage,
        LocalDateTime createdAt,
        LocalDateTime lastMessageAt,
        LocalDateTime lastRead,
        Long unreadCount
) {
}
