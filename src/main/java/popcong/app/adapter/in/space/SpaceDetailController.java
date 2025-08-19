package popcong.app.adapter.in.space;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popcong.app.application.space.service.GetSpaceDetailService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/space")
public class SpaceDetailController {

    private final GetSpaceDetailService getSpaceDetailService;

    @GetMapping("/{spaceId}")
    public ResponseEntity<?> getDetail(@RequestAttribute("userId") Long userId,
                                       @PathVariable Long spaceId) {
        var data = getSpaceDetailService.execute(userId, spaceId);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "공간 상세 조회 성공",
                "data", data
        ));
    }
}