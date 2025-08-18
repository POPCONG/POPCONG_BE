package popcong.app.application.chat.dto.response;

import java.time.LocalDateTime;

public record ChatReadPointers(
        Long guestId,
        Long hostId,
        LocalDateTime guestLastReadAt,
        LocalDateTime hostLastReadAt
) {
}
