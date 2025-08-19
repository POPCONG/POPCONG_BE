package popcong.app.domain.chat.model;

import java.time.LocalDateTime;

public record Message(
        Long messageId,
        Long chatId,
        Long senderId,
        String content,
        LocalDateTime createdAt
) {
}
