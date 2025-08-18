package popcong.app.adapter.in.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popcong.app.application.user.dto.response.MyWishItemDto;
import popcong.app.application.user.service.CheckMyWishlistService;
import popcong.app.domain.user.model.User;
import popcong.app.global.dto.ResponseDto;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/mypage")
public class MypageWishlistController {

    private final CheckMyWishlistService checkMyWishlistService;

    @GetMapping("/wish-list-check")
    public ResponseDto<List<MyWishItemDto>> list(@AuthenticationPrincipal User user) {
        if(user == null)
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        var data = checkMyWishlistService.excute(user.userId());

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "관심목록 조회 성공",
                data
        );
    }
}
