package popcong.app.application.space.port.out;

import java.time.LocalDate;

public interface ReservationPort {
    Long saveBooking(
            Long userId,
            Long spaceId,
            LocalDate startDate,
            LocalDate endDate,
            Integer deposit,
            Integer totalRentalFee
    );
}
