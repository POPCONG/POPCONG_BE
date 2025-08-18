package popcong.app.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import popcong.app.application.user.port.in.LikeSpaceUseCase;
import popcong.app.application.user.port.out.SpaceLookupPort;
import popcong.app.application.user.port.out.WishCommandPort;
import popcong.app.application.user.port.out.WishQueryPort;
import popcong.app.global.exception.custom.BusinessException;
import popcong.app.global.exception.error.SpaceErrorCode;
import popcong.app.global.exception.error.UserErrorCode;

@Service
@RequiredArgsConstructor
public class LikeSpaceService implements LikeSpaceUseCase {

    private final SpaceLookupPort spaceLookupPort;
    private final WishQueryPort wishQueryPort;
    private final WishCommandPort wishCommandPort;

    @Override
    @Transactional
    public LikeSpaceUseCase.Result like(Long userId, Long spaceId) {
        if (spaceId == null || spaceId <= 0) {
            throw new BusinessException(SpaceErrorCode.MISSING_OR_INVALID_SPACE_ID);
        }
        if (!spaceLookupPort.existsById(spaceId)) {
            throw new BusinessException(SpaceErrorCode.SPACE_NOT_FOUND);
        }
        if (wishQueryPort.exists(userId, spaceId)) {
            throw new BusinessException(UserErrorCode.ALREADY_LIKED);
        }

        LocalDateTime now = LocalDateTime.now();
        wishCommandPort.insert(userId, spaceId, now);

        return new LikeSpaceUseCase.Result(userId, spaceId, now.toString());
    }
}