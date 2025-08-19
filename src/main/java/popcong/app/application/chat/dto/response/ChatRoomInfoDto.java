package popcong.app.application.chat.dto.response;

import java.time.LocalDateTime;

public record ChatRoomInfoDto(
        Long chatId,
        String spaceName,
        String address,
        Integer floor,
        Integer rentalFee,
        String coverImage,
        LocalDateTime createdAt,
        Integer maxPeriod
) {
}
