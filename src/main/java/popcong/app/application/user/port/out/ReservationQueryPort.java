package popcong.app.application.user.port.out;

import popcong.app.application.user.dto.response.MyReservationItemDto;

import java.util.List;

public interface ReservationQueryPort {
    List<MyReservationItemDto> findMyReservations(Long userId);
}
