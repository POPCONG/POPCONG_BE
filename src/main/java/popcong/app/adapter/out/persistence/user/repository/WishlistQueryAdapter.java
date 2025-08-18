package popcong.app.adapter.out.persistence.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import popcong.app.application.user.port.out.WishlistQueryPort;

import java.util.Collection;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Transactional
public class WishlistQueryAdapter implements WishlistQueryPort {

    private final WishlistJpaRepository wishlistJpaRepository;

    @Override
    public Set<Long> findWishedSpaceIds(Long userId, Collection<Long> spaceIds) {
        if (userId == null || spaceIds == null || spaceIds.isEmpty()) {
            return Set.of();
        }

        return wishlistJpaRepository
                .findByUser_UserIdAndSpace_SpaceIdIn(userId, spaceIds).stream()
                .map(w -> w.getSpace().getSpaceId())
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public boolean existsByUserIdAndSpaceId(Long userId, Long spaceId) {
        if (userId == null || spaceId == null) return false;
        return wishlistJpaRepository.existsByUser_UserIdAndSpace_SpaceId(userId, spaceId);
    }
}