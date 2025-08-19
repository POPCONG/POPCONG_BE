package popcong.app.application.chat.dto.response;

import java.time.LocalDateTime;

public record MarkReadDto(
        LocalDateTime lastSeenAt
) {
}
