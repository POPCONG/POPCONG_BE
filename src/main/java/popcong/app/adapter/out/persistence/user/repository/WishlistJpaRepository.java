package popcong.app.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import popcong.app.adapter.out.persistence.user.entity.WishlistJpaEntity;
import popcong.app.application.user.port.out.WishCommandPort;
import popcong.app.application.user.port.out.WishQueryPort;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface WishlistJpaRepository extends JpaRepository<WishlistJpaEntity, Long>, WishQueryPort, WishCommandPort {

    // 파생 쿼리 (연관필드.식별자필드)
    boolean existsByUser_UserIdAndSpace_SpaceId(Long userId, Long spaceId);
    void deleteByUser_UserIdAndSpace_SpaceId(Long userId, Long spaceId);
    List<WishlistJpaEntity> findByUser_UserIdAndSpace_SpaceIdIn(Long userId, Collection<Long> spaceIds);

    // WishQueryPort 구현
    @Override
    default boolean exists(Long userId, Long spaceId) {
        return existsByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }

    // WishCommandPort 구현
    @Override
    default Long insert(Long userId, Long spaceId, LocalDateTime createdAt) {
        var entity = WishlistJpaEntity.builder()
                .user(popcong.app.adapter.out.persistence.user.entity.UserJpaEntity.builder().userId(userId).build())
                .space(popcong.app.adapter.out.persistence.space.entity.SpaceJpaEntity.builder().spaceId(spaceId).build())
                .createdAt(createdAt)
                .build();
        return save(entity).getWishlistId();
    }

    @Override
    default void delete(Long userId, Long spaceId) {
        deleteByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }
}