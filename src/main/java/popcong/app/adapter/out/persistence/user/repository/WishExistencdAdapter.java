package popcong.app.adapter.out.persistence.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.application.user.port.out.WishQueryPort;

// exists 포트
@Component
@RequiredArgsConstructor
public class WishExistencdAdapter implements WishQueryPort {
    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public boolean exists(Long userId, Long spaceId) {
        return wishlistJpaRepository.existsByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }
}
