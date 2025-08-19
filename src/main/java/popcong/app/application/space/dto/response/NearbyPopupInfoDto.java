package popcong.app.application.space.dto.response;

import java.time.LocalDateTime;

public record NearbyPopupInfoDto(
        Long popupId,
        String name,
        String address,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}