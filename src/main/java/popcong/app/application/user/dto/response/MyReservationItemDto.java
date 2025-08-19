package popcong.app.application.user.dto.response;

import popcong.app.domain.space.model.PopupStatus;

import java.time.LocalDateTime;

public record MyReservationItemDto(
        PopupStatus status,
        String popupName,
        String address,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
