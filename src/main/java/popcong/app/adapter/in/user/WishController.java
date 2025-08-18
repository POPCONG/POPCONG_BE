// src/main/java/popcong/app/adapter/in/user/WishController.java
package popcong.app.adapter.in.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import popcong.app.application.user.dto.response.WishResponseDto;
import popcong.app.application.user.port.in.LikeSpaceUseCase;
import popcong.app.application.user.port.in.UnlikeSpaceUseCase;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/spaces")
public class WishController {

    private final LikeSpaceUseCase likeSpaceUseCase;
    private final UnlikeSpaceUseCase unlikeSpaceUseCase;

    /**
     * 좋아요 누르기
     * POST /api/v1/user/spaces/{spaceId}/like
     */
    @PostMapping("/{spaceId}/like")
    public ResponseDto<WishResponseDto> like(@PathVariable Long spaceId,
                                             @AuthenticationPrincipal User user) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        var r = likeSpaceUseCase.like(user.userId(), spaceId);
        var dto = new WishResponseDto(r.userId(), r.spaceId(), true, r.createdAtIso());

        log.info("좋아요 누르기 성공: userId={}, spaceId={}", r.userId(), r.spaceId());
        return new ResponseDto<>(
                HttpStatus.CREATED.value(),
                "좋아요 누르기",
                dto
        );
    }

    /**
     * 좋아요 취소
     * DELETE /api/v1/user/spaces/{spaceId}/like
     */
    @DeleteMapping("/{spaceId}/like")
    public ResponseDto<WishResponseDto> unlike(@PathVariable Long spaceId,
                                               @AuthenticationPrincipal User user) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        var r = unlikeSpaceUseCase.unlike(user.userId(), spaceId);
        var dto = new WishResponseDto(r.userId(), r.spaceId(), false, null);

        log.info("좋아요 취소 성공: userId={}, spaceId={}", r.userId(), r.spaceId());
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "좋아요 취소",
                dto
        );
    }
}