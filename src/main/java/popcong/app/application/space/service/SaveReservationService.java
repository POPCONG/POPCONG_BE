package popcong.app.application.space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.space.dto.request.SaveReservationRequestDto;
import popcong.app.application.space.dto.response.SaveReservationResponseDto;
import popcong.app.application.space.port.in.SaveReservationUseCase;
import popcong.app.application.space.port.out.ReservationPort;
import popcong.app.domain.space.model.ReservationStatus;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.ReservationErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class SaveReservationService implements SaveReservationUseCase {

    private final ReservationPort reservationPort;

    @Transactional
    @Override
    public SaveReservationResponseDto save(Long userId, Long spaceId, Long chatId, SaveReservationRequestDto request) {
        LocalDate start = request.startDate();
        LocalDate end   = request.endDate();

        if (start == null || end == null) {
            throw new BusinessException(ReservationErrorCode.RESERVATION_INVALID_DATE_RANGE);
        }

        long day = DAYS.between(start, end);
        if (day <= 0) {
            throw new BusinessException(ReservationErrorCode.RESERVATION_INVALID_DATE_RANGE);
        }

        if (request.rentalFee() == null || request.totalRentalFee() == null || request.deposit() == null) {
            throw new BusinessException(ReservationErrorCode.INVALID_REQUEST);
        }

        long serverCalc = (long) request.rentalFee() * day;
        if (serverCalc != request.totalRentalFee()) {
            throw new BusinessException(ReservationErrorCode.RENTAL_FEE_MISMATCH);
        }

        // Port에서 saveBooking 후 ReservationId 반환
        Long reservationId = reservationPort.saveBooking(
                userId, spaceId, start, end, request.deposit(), request.totalRentalFee()
        );

        // ReservationStatus는 엔티티 저장 시 기본 BOOKING으로 두었으면 BOOKING을 그대로 사용
        ReservationStatus status = ReservationStatus.BOOKING;

        return new SaveReservationResponseDto(
                reservationId,
                userId,
                spaceId,
                status,
                start,
                end,
                request.deposit(),
                request.totalRentalFee(),
                LocalDateTime.now()
        );
    }
}