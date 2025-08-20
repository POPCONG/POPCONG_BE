package popcong.app.adapter.in.space;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import popcong.app.application.space.dto.response.SpaceDetailResponseDto;
import popcong.app.application.space.service.GetSpaceDetailService;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/space")
public class SpaceDetailController {

    private final GetSpaceDetailService getSpaceDetailService;

    @GetMapping("/{spaceId}")
    public ResponseDto<SpaceDetailResponseDto> getDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long spaceId
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        SpaceDetailResponseDto data = getSpaceDetailService.execute(user.userId(), spaceId);

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "매물 상세 정보 조회 성공",
                data
        );
    }
}