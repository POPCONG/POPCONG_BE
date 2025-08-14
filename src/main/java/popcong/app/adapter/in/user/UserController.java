package popcong.app.adapter.in.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popcong.app.application.user.dto.response.UserResponseDto;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/me")
    public ResponseDto<UserResponseDto> getUserInfo(@AuthenticationPrincipal User user) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        log.info("사용자 조회 성공 : userId = {}, providerId = {}, email = {}", user.userId(), user.providerId(), user.email());

        UserResponseDto result = UserResponseDto.from(user);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "사용자 조회 성공",
                result
        );
    }
}
