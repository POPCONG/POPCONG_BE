package popcong.app.adapter.in.space;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import popcong.app.application.space.dto.request.SaveReservationRequestDto;
import popcong.app.application.space.dto.response.SaveReservationResponseDto;
import popcong.app.application.space.port.in.SaveReservationUseCase;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces/{spaceId}/chats/{chatId}/reservations")
public class ReservationController {

    private final SaveReservationUseCase saveReservationUseCase;

    @PostMapping
    public ResponseDto<SaveReservationResponseDto> createReservation(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @PathVariable Long spaceId,
            @PathVariable Long chatId,
            @AuthenticationPrincipal User user,
            @RequestBody SaveReservationRequestDto request
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        log.info("예약 생성 요청: userId={}, spaceId={}, chatId={}, start={}, end={}",
                user, spaceId, chatId, request.startDate(), request.endDate());

        SaveReservationResponseDto result =
                saveReservationUseCase.save(user.userId(), spaceId, chatId, request);

        return new ResponseDto<>(
                HttpStatus.CREATED.value(),
                "예약이 생성되었습니다.",
                result
        );
    }
}