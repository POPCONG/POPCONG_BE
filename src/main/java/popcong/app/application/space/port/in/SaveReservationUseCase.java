package popcong.app.application.space.port.in;


import popcong.app.application.space.dto.request.SaveReservationRequestDto;
import popcong.app.application.space.dto.response.SaveReservationResponseDto;

public interface SaveReservationUseCase {
    SaveReservationResponseDto save(Long userId, Long spaceId, Long chatId, SaveReservationRequestDto request);
}