package popcong.app.domain.space.model;

import java.time.LocalDateTime;

public record Popup(
        Long popupId,
        Long reservationId,
        String name,
        PopupStatus popupStatus,
        String address,
        PopupType popupType,
        Integer price,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer views
) {
}
