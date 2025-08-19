package popcong.app.application.chat.dto.response;

import java.time.LocalDateTime;

public record MessageDto(
        Long messageId,
        Long chatId,
        Long senderId,
        String message,
        LocalDateTime createdAt
) {
}
