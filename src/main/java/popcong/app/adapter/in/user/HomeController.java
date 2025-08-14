package popcong.app.adapter.in.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import popcong.app.application.user.dto.response.HomeUserResponseDto;
import popcong.app.application.user.port.in.GetHomeInfoUseCase;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/popup/home")
public class HomeController {

    private final GetHomeInfoUseCase getHomeInfoUseCase;

    @GetMapping("/me")
    public ResponseDto<HomeUserResponseDto> getHomeInfo(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        HomeUserResponseDto result =
                getHomeInfoUseCase.getMyHomeInfo(user.userId(), limit, offset);

        log.info("홈 정보 조회 성공 : userId={}", user.userId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "홈 정보 조회 성공",
                result
        );
    }
}