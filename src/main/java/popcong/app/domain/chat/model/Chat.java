package popcong.app.domain.chat.model;

import java.time.LocalDateTime;

public record Chat(
        Long chatId,
        Long guestId,
        Long hostId,
        Long spaceId,
        LocalDateTime createdAt,
        LocalDateTime lastMessageAt,
        LocalDateTime lastReadAt,
        LocalDateTime guestLastReadAt,
        LocalDateTime hostLastReadAt
) {
}
