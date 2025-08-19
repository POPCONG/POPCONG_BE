package popcong.app.adapter.in.space;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import popcong.app.application.space.service.GetSpaceDetailService;
import popcong.app.domain.user.model.User;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.AuthErrorCode;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/space")
public class SpaceDetailController {

    private final GetSpaceDetailService getSpaceDetailService;

    @GetMapping("/{spaceId}")
    public ResponseEntity<?> getDetail(
            @AuthenticationPrincipal User user,
//            @RequestAttribute("userId") Long userId,
            @PathVariable Long spaceId
    ) {
        if (user == null) throw new BusinessException(AuthErrorCode.UNAUTHORIZED);

        var data = getSpaceDetailService.execute(user.userId(), spaceId);

        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "공간 상세 조회 성공",
                "data", data
        ));
    }
}