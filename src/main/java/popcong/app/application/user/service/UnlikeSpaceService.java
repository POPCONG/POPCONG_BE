package popcong.app.application.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.port.in.UnlikeSpaceUseCase;
import popcong.app.application.user.port.out.WishCommandPort;
import popcong.app.application.user.port.out.WishQueryPort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;
import popcong.app.global.exception.error.UserErrorCode;

@Service
@RequiredArgsConstructor
public class UnlikeSpaceService implements UnlikeSpaceUseCase {

    private final WishQueryPort wishQueryPort;
    private final WishCommandPort wishCommandPort;

    @Override
    @Transactional
    public UnlikeSpaceUseCase.Result unlike(Long userId, Long spaceId) {  // ← 철자 정확히
        if (spaceId == null || spaceId <= 0) {
            throw new BusinessException(SpaceErrorCode.MISSING_OR_INVALID_SPACE_ID);
        }
        if (!wishQueryPort.exists(userId, spaceId)) {
            throw new BusinessException(UserErrorCode.NOT_LIKED);
        }

        wishCommandPort.delete(userId, spaceId);
        return new UnlikeSpaceUseCase.Result(userId, spaceId);            // ← 여기도 동일 철자
    }
}