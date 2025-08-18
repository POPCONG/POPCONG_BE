package popcong.app.adapter.out.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity;
import popcong.app.adapter.out.persistence.user.entity.UserJpaEntity;
import popcong.app.adapter.out.persistence.user.entity.WishlistJpaEntity;
import popcong.app.adapter.out.persistence.user.repository.WishlistJpaRepository;
import popcong.app.application.user.port.out.WishCommandPort;

import java.time.LocalDateTime;

//insert/delete 포트
@Component
@RequiredArgsConstructor
public class WishCommandAdapter implements WishCommandPort {
    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public Long insert(Long userId, Long spaceId, LocalDateTime createdAt) {
        var entity = WishlistJpaEntity.builder()
                .user(UserJpaEntity.builder().userId(userId).build())
                .space(SpaceJpaEntity.builder().spaceId(spaceId).build())
                .createdAt(createdAt)
                .build();
        return wishlistJpaRepository.save(entity).getWishlistId();
    }

    @Override
    public void delete(Long userId, Long spaceId) {
        wishlistJpaRepository.deleteByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }
}
