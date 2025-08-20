package popcong.app.application.space.dto.response;

import popcong.app.domain.space.model.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SaveReservationResponseDto(
        Long reservationId,
        Long userId,
        Long spaceId,
        ReservationStatus reservationStatus, // BOOKING/IN_PROGRESS/DONE/CANCELLED
        LocalDate startDate,
        LocalDate endDate,
        Integer deposit,
        Integer totalRentalFee,
        LocalDateTime createdAt
        ) { }
