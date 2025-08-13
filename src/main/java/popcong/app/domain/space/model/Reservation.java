package popcong.app.domain.space.model;

import java.time.LocalDateTime;

public record Reservation(
        Long reservationId,
        Long userId,
        Long spaceId,
        LocalDateTime reservationDate, //예약 확정 날짜
        LocalDateTime startTime, //사용 시작 날짜
        LocalDateTime endTime, //사용 종료 날짜
        ReservationStatus status,
        Integer totalRentalFee, //대여료
        Integer Deposit //보증금

) {
}
